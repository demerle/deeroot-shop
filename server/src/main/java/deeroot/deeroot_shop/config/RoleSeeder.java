
package deeroot.deeroot_shop.config;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.Role;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.repositories.RoleRepository;
import deeroot.deeroot_shop.repositories.UserRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MusicItemService musicItemService;



    @Override
    public void run(String... args) {

        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");


        /*
        //Creating stub User for testing
        userRepository.findByEmail("dumb@gmail.com").orElseGet(() -> {
            User newUser = User.builder()
                    .email("dumb@gmail.com")
                    .password(passwordEncoder.encode("dumbPassword"))
                    .build();

            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role of User not Found"));
            newUser.getRoles().add(userRole);

            return userRepository.save(newUser);
        });

        System.out.println("Seeded base user");

         */


        //Creating stub admin user for testing

        userRepository.findByEmail("danny.emerle@gmail.com").orElseGet(() -> {
            User newUser = User.builder()
                    .email("danny.emerle@gmail.com")
                    .password(passwordEncoder.encode("Dumbass1234!"))
                    .build();


            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role of Admin not Found"));
            newUser.getRoles().add(adminRole);



            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role of User not Found"));
            newUser.getRoles().add(userRole);


            return userRepository.save(newUser);
        });

        System.out.println("Seeded base admin user");




    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Seeded role: " + roleName);
        }
    }


}

