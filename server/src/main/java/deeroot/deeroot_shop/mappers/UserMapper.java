package deeroot.deeroot_shop.mappers;

import deeroot.deeroot_shop.domain.dto.UserDto;
import deeroot.deeroot_shop.domain.entities.User;

public interface UserMapper {

    User fromUserDto(UserDto userDto);

    UserDto toUserDto(User user);
}
