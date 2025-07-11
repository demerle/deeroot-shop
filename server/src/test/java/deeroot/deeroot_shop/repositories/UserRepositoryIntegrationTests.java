package deeroot.deeroot_shop.repositories;

import deeroot.deeroot_shop.TestDataUtil;
import deeroot.deeroot_shop.domain.entities.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class UserRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryIntegrationTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testThatUsersCanBeCreated(){
        User user = TestDataUtil.createTestUserA();
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testThatUsersCanBeUpdated() {
        User user = TestDataUtil.createTestUserA();
        userRepository.save(user);

        user.setName("UPDATED");

        userRepository.save(user);

        Optional<User> result = userRepository.findById(user.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testMusicItemsCanBeDeleted() {
        User user = TestDataUtil.createTestUserA();
        userRepository.save(user);
        userRepository.delete(user);

        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void testForFindAll(){
        User user1 = TestDataUtil.createTestUserA();
        User user2 = TestDataUtil.createTestUserB();
        userRepository.save(user1);
        userRepository.save(user2);


        List<User> result = userRepository.findAll();

        assertThat(result).hasSize(2).containsExactly(user1, user2);
    }


}
