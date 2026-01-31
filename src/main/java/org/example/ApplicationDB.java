package org.example;


import org.example.entity.UserEntity;
import org.example.repository.EntityRepository;
import org.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ApplicationDB {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDB.class, args);
    }

    @Bean
    CommandLineRunner app(UserService userService, EntityRepository repository) {
        return args -> {
            UserEntity user = userService.createUser("new_user");
            System.out.println("Created user: " + user);

            List<UserEntity> users = userService.getAllUsers();
            System.out.println("All users: " + users);

            UserEntity user2 = userService.getUserById(user.getId());
            System.out.println("Got one user: " + user2);

            UserEntity updatedUser = userService.updateUser(user.getId(), "new_new_user");
            System.out.println("Updated user: " + updatedUser);

            userService.deleteUser(user.getId());
            System.out.println("Deleted user #: " + user.getId());

        };

    }

}