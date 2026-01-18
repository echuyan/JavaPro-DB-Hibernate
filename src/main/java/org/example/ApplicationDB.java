package org.example;


import org.example.config.DbConfig;
import org.example.datamodel.User;
import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan
@EnableTransactionManagement
public class ApplicationDB {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class,ApplicationDB.class);

        UserService userService = context.getBean(UserService.class);


        userService.createUser(new User(1L, "Elena1"));
        userService.createUser(new User(2L, "Elena2"));
        userService.createUser(new User(3L, "Elena3"));
        userService.createUser(new User(4L, "Elena4"));
        System.out.println(userService.getAllUsers());
        System.out.println(userService.getUserById(4L));
        User testUser = new User(4L,"testUser");
        userService.updateUser(testUser);
        System.out.println(userService.getUserById(4L));
        userService.deleteUser(4L);
        System.out.println(userService.getAllUsers());
        context.close();

    }
}