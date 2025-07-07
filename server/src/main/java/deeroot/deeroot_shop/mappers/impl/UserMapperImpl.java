package deeroot.deeroot_shop.mappers.impl;

import deeroot.deeroot_shop.domain.dto.UserDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User fromUserDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
