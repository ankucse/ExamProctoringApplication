/**
 * WebRTC Streaming Setup (Front-End)
 * Captures video and audio and prepares it for a peer connection.
 */

let localStream;

async function startCamera() {
    try {
        const constraints = {
            video: {
                width: { ideal: 1280 },
                height: { ideal: 720 },
                facingMode: "user" // front camera
            },
            audio: true
        };

        localStream = await navigator.mediaDevices.getUserMedia(constraints);
        
        // Assuming there is a <video id="localVideo" autoplay></video> element
        const videoElement = document.getElementById('localVideo');
        if (videoElement) {
            videoElement.srcObject = localStream;
        }

        console.log("WebRTC Stream Started");
        // Next: Initiate RTCPeerConnection and Signaling (Offer/Answer/ICE)
        // to send this stream to a media server (like Kurento or raw SFU)

    } catch (err) {
        console.error("Error accessing media devices.", err);
        sendEvent('CAMERA_DENIED', 'CRITICAL', { error: err.message });
    }
}
