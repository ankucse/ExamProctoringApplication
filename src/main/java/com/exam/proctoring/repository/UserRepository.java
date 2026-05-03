package com.exam.proctoring.repository;

import com.exam.proctoring.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Finds a user by their email address.
     * @param email The email to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByEmail(String email);
}
