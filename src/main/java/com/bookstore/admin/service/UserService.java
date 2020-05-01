package com.bookstore.admin.service;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.PasswordResetToken;
import com.bookstore.admin.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    void save(User user);
}
