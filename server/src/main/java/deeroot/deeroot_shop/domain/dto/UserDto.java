package deeroot.deeroot_shop.domain.dto;

import deeroot.deeroot_shop.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();


}
