package deeroot.deeroot_shop.services.impl;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.repositories.MusicItemRepository;
import deeroot.deeroot_shop.repositories.UserRepository;
import deeroot.deeroot_shop.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private MusicItemRepository musicItemRepository;

    public UserServiceImpl(UserRepository userRepository, MusicItemRepository musicItemRepository) {
        this.userRepository = userRepository;
        this.musicItemRepository = musicItemRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> find(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void clearAllOwnedMusicItems(User user) {
        Set<MusicItem> ownedItems = user.getOwnedMusicItems();
        for (MusicItem musicItem : ownedItems) {
            musicItemRepository.deleteById(musicItem.getId());
        }
        ownedItems.clear();
        userRepository.save(user);

    }
}
