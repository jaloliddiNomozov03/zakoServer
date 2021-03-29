package uz.zako.example.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.zako.example.entity.Role;
import uz.zako.example.entity.User;
import uz.zako.example.repository.RoleRepository;
import uz.zako.example.repository.UserRepository;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(1l, "ROLE_ADMIN");
        Role roleTeacher = new Role(2l, "ROLE_TEACHER");
        Role roleStudent = new Role(3l, "ROLE_STUDENT");
        try {
            roleRepository.save(roleAdmin);
            roleRepository.save(roleTeacher);
            roleRepository.save(roleStudent);
        } catch (Exception e) {
            System.out.println(e);
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode("zakoadmin123"));
        user.setRoles(roleRepository.findAll());
        user.setUsername("zakoadmin");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setUserType("ADMIN");
        user.setId(1l);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
