
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

        userRepository.findByEmail("dumb@gmail.com").orElseGet(() -> {
            User newUser = User.builder()
                    .email("dumb@gmail.com")
                    .password(passwordEncoder.encode("dumbPassword"))
                    .build();
            MusicItem item = MusicItem.builder()
                    .title("Moonlight Sonata")
                    .description("Movement 3")
                    .composer("Beethoven")
                    .price(10.00)
                    .fileName("moonlight.pdf")
                    .fileType("application/pdf")
                    .imageFileName("moonlight.png")
                    .imageFileType("image/png")
                    .build();


            musicItemService.save(item);
            newUser.getOwnedMusicItems().add(item);

            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role of User not Found"));
            newUser.getRoles().add(userRole);

            return userRepository.save(newUser);
        });
        System.out.println("Seeded base user");

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

