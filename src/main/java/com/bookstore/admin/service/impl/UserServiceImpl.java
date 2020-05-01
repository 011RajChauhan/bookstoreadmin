package com.bookstore.admin.service.impl;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.PasswordResetToken;
import com.bookstore.admin.domain.security.UserRole;
import com.bookstore.admin.repository.PasswordResetTokenRepository;
import com.bookstore.admin.repository.RoleRepository;
import com.bookstore.admin.repository.UserRepository;
import com.bookstore.admin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles)  {
       User newUser =  userRepository.findByUsername(user.getUsername());
       if(newUser!=null){
           logger.info("user {} already exists, nothing can be done.",newUser);
       }else{
           for(UserRole ur: userRoles) {
               roleRepository.save(ur.getRole());
           }
           user.getUserRoles().addAll(userRoles);
           newUser = userRepository.save(user);
       }
       return newUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
