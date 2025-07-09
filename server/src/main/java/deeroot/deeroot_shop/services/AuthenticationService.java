package deeroot.deeroot_shop.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String usernameOrEmail, String password);
    String generateToken(UserDetails userDetails);
}
