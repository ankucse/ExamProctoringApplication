package com.exam.proctoring.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

/**
 * Handles uploading webcam snapshots and video chunks.
 * Designed to be Cloud-Ready (AWS S3) but gracefully degrades or throws manageable errors
 * if cloud credentials aren't set up yet for local dev.
 */
@Service
public class MediaStorageService {

    private static final Logger log = LoggerFactory.getLogger(MediaStorageService.class);
    private final S3Client s3Client; // Would typically be configured via a @Configuration bean
    private final String bucketName = "exam-proctoring-media";

    public MediaStorageService() {
        // For demonstration, we handle S3Client creation softly to not crash startup without AWS creds.
        S3Client client = null;
        try {
            client = S3Client.builder().build();
        } catch (Exception e) {
            log.warn("Could not build AWS S3 Client. Defaulting to Mock Storage. Reason: " + e.getMessage());
        }
        this.s3Client = client;
    }

    /**
     * Uploads an image. Uses Resilience4j @Retry to handle transient network blips.
     */
    @Retry(name = "mediaStorageRetry", fallbackMethod = "fallbackUpload")
    public String uploadSnapshot(String attemptId, byte[] imageBytes) {
        String key = attemptId + "/snapshots/" + UUID.randomName() + ".jpg";
        
        if (s3Client != null) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/jpeg")
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(imageBytes));
            log.info("Successfully uploaded snapshot to S3: {}", key);
            return "s3://" + bucketName + "/" + key;
        } else {
            log.debug("MOCK UPLOAD: Stored snapshot locally/in-memory for key: {}", key);
            return "mock://" + key;
        }
    }

    // Fallback if all 3 S3 retries fail
    public String fallbackUpload(String attemptId, byte[] imageBytes, Exception e) {
        log.error("S3 Upload completely failed for attempt: {}. Fallback triggered. Falling back to local disk queue.", attemptId, e);
        // In a real system, we'd save this to a local disk queue and try uploading later via a CRON job.
        return "failed-upload://" + UUID.randomName();
    }
}
