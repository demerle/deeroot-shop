package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.dto.UserDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.Role;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.mappers.UserMapper;
import deeroot.deeroot_shop.repositories.RoleRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final RoleRepository roleRepository;
    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final MusicItemMapper musicItemMapper;

    private final PasswordEncoder passwordEncoder;
    private final MusicItemService musicItemService;

    public UserController(UserServiceImpl userService, UserMapper userMapper, PasswordEncoder passwordEncoder, MusicItemMapper musicItemMapper, RoleRepository roleRepository, MusicItemService musicItemService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.musicItemMapper = musicItemMapper;
        this.roleRepository = roleRepository;
        this.musicItemService = musicItemService;
    }

    @GetMapping(path = "/users/owned-items")
    public List<MusicItemDto> getUsersOwnedMusicItems(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null){
            log.info("Null userDetails in UserOwnedMusicItems getMapping");
            return Collections.emptyList();
        }
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }
        List<MusicItemDto> result = Optional.ofNullable(user.getOwnedMusicItems())
                .orElse(Collections.emptySet())
                .stream()
                .map(musicItemMapper::toMusicItemDto)
                .toList();
        return result;
    }

    @GetMapping(path = "/users/owned-items/{id}")
    public ResponseEntity<Boolean> checkIfUserOwnsMusicItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        if (userDetails == null){
            log.info("Null userDetails in checkIfUserOwnsMusicItem getMapping");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            log.info("Null user in checkIfUserOwnsMusicItem getMapping");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MusicItem curr = musicItemService.find(id).orElse(null);
        if (curr == null) {
            log.info("Null music item by id in checkIfUserOwnsMusicItem getMapping");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean res = user.getOwnedMusicItems().contains(curr);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    @PutMapping(path = "/users/owned-items")
    public ResponseEntity<Boolean> updateUsersOwnedMusicItems(@RequestBody List<MusicItemDto> list, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null){
            log.info("Null userDetails in UserOwnedMusicItems PutMapping");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (MusicItemDto item : list){
            MusicItem mapped = musicItemMapper.fromMusicItemDto(item);
            user.getOwnedMusicItems().add(mapped);
        }
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping(path = "/users")
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            log.info("Null userDetails in getUser getMapping");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);

    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){

        User userEntity = userMapper.fromUserDto(dto);

        if (userService.findByEmail(userEntity.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role of User not Found"));
        userEntity.getRoles().add(userRole);


        User saved = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.toUserDto(saved), HttpStatus.CREATED);
    }

    @PutMapping(path = "/users")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto){
        User userEntity = userMapper.fromUserDto(dto);
        User saved = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.toUserDto(saved), HttpStatus.OK);
    }

}
