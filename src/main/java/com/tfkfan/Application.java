package com.tfkfan;

import com.tfkfan.hibernate.dao.RoleDao;
import com.tfkfan.hibernate.dao.UserDao;
import com.tfkfan.hibernate.entities.Role;
import com.tfkfan.hibernate.entities.User;
import com.tfkfan.security.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;


@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class Application implements CommandLineRunner {

    @Autowired
    UserDao userRepository;

    
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
    	/*
        Arrays.stream(UserRole.values()).forEach(x -> {
            String name = x.getRole().split("_")[1].toLowerCase();

            User user = new User();
            user.setUsername(name);
            user.setPassword(passwordEncoder.encode("1234"));

            Role role = new Role();
            role.setId(3L);
            //role.setRole(x);
            //role.setUser(user);

            user.setRole(role);
            userRepository.save(user);
        });
        */
    }
}
