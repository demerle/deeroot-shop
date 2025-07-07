package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.dto.UserDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.UserMapper;
import deeroot.deeroot_shop.services.UserService;
import deeroot.deeroot_shop.services.impl.UserServiceImpl;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserServiceImpl userService;

    private UserMapper userMapper;

    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers(){
        List<User> users = userService.findAll();
        return users.stream()
                .map(item -> userMapper.toUserDto(item))
                .toList();
    }


}
