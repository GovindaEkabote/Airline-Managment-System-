package com.airline.repository;

import com.airline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u
        FROM User u
        WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<User> searchUsers(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
