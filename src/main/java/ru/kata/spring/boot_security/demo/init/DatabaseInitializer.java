package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer {
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public DatabaseInitializer(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (userService.showUsers().isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            roleRepository.save(userRole);

            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);
            User adminUser = new User("admin", "adminov", 80L, "adminAdminov@ban.dam", "admin", adminRoles);
            userService.save(adminUser);

            List<Role> userRoles = new ArrayList<>();
            userRoles.add(userRole);
            User regularUser = new User("user", "userov", 1L, "userUserov@prav.net", "user", userRoles);
            regularUser.setRoles(userRoles);
            userService.save(regularUser);
        }
    }
}
