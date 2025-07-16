package deeroot.deeroot_shop.services;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> find(Long id);

    User save(User musicItem);

    // User partialUpdate(User musicItem);

    void delete(Long id);

    Optional<User> findByEmail(String email);

    void clearAllOwnedMusicItems(User user);
}
