package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.dto.UserDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.mappers.UserMapper;
import deeroot.deeroot_shop.services.UserService;
import deeroot.deeroot_shop.services.impl.UserServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController {

    private UserServiceImpl userService;

    private UserMapper userMapper;

    private MusicItemMapper musicItemMapper;

    private PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userService, UserMapper userMapper, PasswordEncoder passwordEncoder, MusicItemMapper musicItemMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.musicItemMapper = musicItemMapper;
    }

    @GetMapping(path = "/users")
    public List<MusicItemDto> getUsersOwnedMusicItems(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        User user = userService.findByEmail(email).orElse(null);
        List<MusicItemDto> result = Optional.ofNullable(user.getOwnedMusicItems())
                .orElse(Collections.emptySet())
                .stream()
                .map(musicItem -> musicItemMapper.toMusicItemDto(musicItem))
                .toList();
        return result;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){

        User userEntity = userMapper.fromUserDto(dto);

        if (userService.findByEmail(userEntity.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        log.info(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
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
