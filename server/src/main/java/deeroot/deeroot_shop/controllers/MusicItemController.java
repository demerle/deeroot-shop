package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.impl.MusicItemServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class MusicItemController {

    MusicItemServiceImpl musicItemService;

    MusicItemMapper musicItemMapper;

    public MusicItemController(MusicItemServiceImpl musicItemService, MusicItemMapper musicItemMapper) {
        this.musicItemService = musicItemService;
        this.musicItemMapper = musicItemMapper;
    }

    @GetMapping(path = "/music-items")
    public List<MusicItemDto> listMusicItems(){
        List<MusicItem> musicItems = musicItemService.findAll();
        return musicItems.stream()
                .map(item -> musicItemMapper.toMusicItemDto(item))
                .toList();
    }


}
