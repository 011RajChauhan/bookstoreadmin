package com.bookstore.admin;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.Role;
import com.bookstore.admin.domain.security.UserRole;
import com.bookstore.admin.service.UserService;
import com.bookstore.admin.utility.SecurityUtility;
import com.bookstore.admin.utility.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BookstoreAdminApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreAdminApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 User user = new User();

        user.setFirstName("Rajan");
        user.setLastName("Chauhan");
        user.setEmail("011rajchauhan@gmail.com");
        user.setUsername("011rajchauhan");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        Set<UserRole> userRoles = new HashSet<>();
        Role role = new Role();
        role.setName("ADMIN_ROLE");
        role.setRoleId(0);
        userRoles.add(new UserRole(user,role));

        userService.createUser(user,userRoles);
	}
}
