package deeroot.deeroot_shop.repositories;

import deeroot.deeroot_shop.TestDataUtil;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.impl.MusicItemServiceImpl;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
public class MusicItemRepositoryIntegrationTests {

    @Autowired
    private MusicItemRepository testMusicItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public MusicItemRepositoryIntegrationTests(MusicItemRepository testMusicItemRepository) {
        this.testMusicItemRepository = testMusicItemRepository;
    }

    @Test
    public void testMusicItemsCanBeCreated() {
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        testMusicItemRepository.save(musicItem);
        Optional<MusicItem> result = testMusicItemRepository.findById(musicItem.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(musicItem.getId());
    }

    @Test
    public void testMusicItemsCanBeUpdated() {
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        testMusicItemRepository.save(musicItem);

        musicItem.setDescription("UPDATED");

        testMusicItemRepository.save(musicItem);

        Optional<MusicItem> result = testMusicItemRepository.findById(musicItem.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(musicItem);
    }

    @Test
    public void testMusicItemsCanBeDeleted() {
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        testMusicItemRepository.save(musicItem);
        testMusicItemRepository.delete(musicItem);

        Optional<MusicItem> result = testMusicItemRepository.findById(musicItem.getId());
        assertThat(result).isNotPresent();
    }


    @Test
    @BeforeEach
    public void testForFindAll(){
        userRepository.deleteAll();
        testMusicItemRepository.deleteAll();
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        MusicItem musicItem2 = TestDataUtil.createTestMusicItemB();

        testMusicItemRepository.save(musicItem);
        testMusicItemRepository.save(musicItem2);


        List<MusicItem> result = testMusicItemRepository.findAll();

        assertThat(result).hasSize(2).containsExactly(musicItem, musicItem2);
    }



    @Test
    public void emptyTest(){

    }




}
