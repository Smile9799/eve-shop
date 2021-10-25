package com.eve_coding.eveshop;

import com.eve_coding.eveshop.model.Role;
import com.eve_coding.eveshop.model.User;
import com.eve_coding.eveshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EveShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EveShopApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(UserService userService){
//        return args -> {
//
//            Role role = new Role();
//            role.setRoleName("USER");
//            role.setRoleDescription("Basic level");
//
//            userService.saveRole(role);
//
//            Role role1 = new Role();
//            role1.setRoleName("ADMIN");
//            role1.setRoleDescription("Site Admin");
//
//            userService.saveRole(role1);
//            User user = new User();
//            user.setFirstName("Eversmile");
//            user.setLastName("Muthanuni");
//            user.setEmail("muthanunie@gmail.com");
//            user.setPassword("Eve@123");
//
//            userService.saveUser(user);
//        };
//    }
}
