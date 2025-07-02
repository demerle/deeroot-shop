package deeroot.deeroot_shop.services.impl;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.repositories.MusicItemRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicItemServiceImpl implements MusicItemService {

    private MusicItemRepository musicItemRepository;

    private MusicItemMapper musicItemMapper;

    public MusicItemServiceImpl(MusicItemRepository musicItemRepository, MusicItemMapper musicItemMapper) {
        this.musicItemRepository = musicItemRepository;
        this.musicItemMapper = musicItemMapper;
    }

    @Override
    public List<MusicItem> findAll() {
        return musicItemRepository.findAll();
    }


    @Override
    public Optional<MusicItem> find(Long id) {
        return musicItemRepository.findById(id);
    }

    @Override
    public MusicItem save(MusicItem musicItem) {
        return musicItemRepository.save(musicItem);
    }

    /*
    @Override
    public MusicItem partialUpdate(MusicItem musicItem) {
        return null;
    }
    */

    @Override
    public void delete(Long id) {

    }
}
