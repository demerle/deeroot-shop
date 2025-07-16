package deeroot.deeroot_shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import deeroot.deeroot_shop.TestDataUtil;
import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.repositories.MusicItemRepository;
import deeroot.deeroot_shop.repositories.UserRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.UserService;
import deeroot.deeroot_shop.services.impl.MusicItemServiceImpl;
import deeroot.deeroot_shop.services.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Rollback
@AutoConfigureMockMvc
public class MusicItemControllerIntegrationTests {

    @Autowired
    private MusicItemServiceImpl service;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MusicItemServiceImpl musicItemServiceImpl;
    @Autowired
    private MusicItemRepository musicItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    @WithMockUser(username = "user", roles="ADMIN")
    public void testThatListALlMusicItemsReturnsHttp200() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/music-items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    @WithMockUser(username = "user", roles="ADMIN")
    public void testThatListAllMusicItemsReturnsAccurateJson() throws Exception {
        if (!userRepository.findAll().isEmpty()) {
            List<User> list = userRepository.findAll();
            for (User user : list) {
                userServiceImpl.clearAllOwnedMusicItems(user);
            }
        }

        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        MusicItem savedItem = service.save(musicItem);

        mvc.perform(
                MockMvcRequestBuilders.get("/music-items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(savedItem.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(savedItem.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].description").value(savedItem.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].composer").value(savedItem.getComposer())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].price").value(savedItem.getPrice())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].fileName").value(savedItem.getFileName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].fileType").value(savedItem.getFileType())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].imageFileName").value(savedItem.getImageFileName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].imageFileType").value(savedItem.getImageFileType())
        );
    }

    @Test
    @WithMockUser(username = "user", roles="ADMIN")
    public void testThatFindOneMusicItemReturnsHttp200() throws Exception {
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        MusicItem savedItem = service.save(musicItem);

        mvc.perform(
                MockMvcRequestBuilders.get("/music-items/" + savedItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @WithMockUser(username = "user", roles="ADMIN")
    public void testThatFindOneMusicItemsReturnsAccurateJson() throws Exception {
        MusicItem musicItem = TestDataUtil.createTestMusicItemA();
        MusicItem savedItem = service.save(musicItem);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/music-items/" + savedItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").value(savedItem.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("title").value(savedItem.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("description").value(savedItem.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("composer").value(savedItem.getComposer())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("price").value(savedItem.getPrice())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("fileName").value(savedItem.getFileName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("fileType").value(savedItem.getFileType())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("imageFileName").value(savedItem.getImageFileName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("imageFileType").value(savedItem.getImageFileType())
        );
    }









}
