package com.bookstore.admin.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecurityUtility {

        private static final String SALT = "salt";

        /*this bean is not used anywhere*/
        @Bean
        public static BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
        }

        @Bean
    public static String randomPassword() {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQUSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            /*gives a random value from 0.1 - 0.9*/
            Random random = new Random();
            while (salt.length() < 18) {
                int index = (int)(random.nextFloat()*SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltstr = salt.toString();
            return saltstr;
        }
}
