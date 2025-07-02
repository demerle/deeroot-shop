package deeroot.deeroot_shop.services;

import deeroot.deeroot_shop.domain.entities.MusicItem;

import java.util.List;
import java.util.Optional;

public interface MusicItemService {

    List<MusicItem> findAll();

    Optional<MusicItem> find(Long id);

    MusicItem save(MusicItem musicItem);

   // MusicItem partialUpdate(MusicItem musicItem);

    void delete(Long id);


}
