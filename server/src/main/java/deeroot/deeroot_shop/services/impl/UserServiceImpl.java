package deeroot.deeroot_shop.services.impl;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.repositories.MusicItemRepository;
import deeroot.deeroot_shop.repositories.UserRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private MusicItemRepository musicItemRepository;

    private MusicItemService musicItemService;

    private MusicItemMapper musicItemMapper;

    public UserServiceImpl(UserRepository userRepository, MusicItemRepository musicItemRepository, MusicItemMapper musicItemMapper, MusicItemService musicItemService) {
        this.userRepository = userRepository;
        this.musicItemRepository = musicItemRepository;
        this.musicItemMapper = musicItemMapper;
        this.musicItemService = musicItemService;
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

    @Override
    public void emptyCart(User user) {
        Set<MusicItem> cart = user.getShoppingCart();
        for (MusicItem musicItem : cart) {
            cart.remove(musicItem);
        }
        userRepository.save(user);

    }

    @Override
    public void emptyPurchasedItems(User user) {
        Set<MusicItem> purchased = user.getPurchasedItems();
        for (MusicItem musicItem : purchased) {
            purchased.remove(musicItem);
        }
        userRepository.save(user);
    }

    public void updateUsersOwnedItemsWithNewItems(UserDetails userDetails, List<MusicItem> list){

        String email = userDetails.getUsername();
        User user = findByEmail(email).orElse(null);

        if (user == null){
            System.out.println("null user in updateUsersOwnedItemsWithNewItems");
            return;
        }
        for (MusicItem item : list){
            user.getOwnedMusicItems().add(item);
        }
        emptyCart(user);
        userRepository.save(user);
    }
}
