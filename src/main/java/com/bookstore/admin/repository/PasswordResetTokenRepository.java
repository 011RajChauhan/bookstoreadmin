package com.bookstore.admin.repository;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryTimeLessThan(Date now);

   /* @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryTime <= ? 1")
    void deleteAllExpiredSince(Date now);*/
}
