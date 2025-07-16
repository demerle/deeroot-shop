package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.impl.MusicItemServiceImpl;
import jakarta.annotation.Resource;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping(path = "/music-items/{id}")
    public ResponseEntity<MusicItemDto> getMusicItem(@PathVariable Long id){
        Optional<MusicItem> foundItem = musicItemService.find(id);
        return foundItem.map(musicItemEntity -> {
            MusicItemDto musicItemDto = musicItemMapper.toMusicItemDto(musicItemEntity);
            return new ResponseEntity<>(musicItemDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping(path = "/music-items")
    public ResponseEntity<MusicItemDto> createAuthor(@RequestBody MusicItemDto dto){
        MusicItem musicItemEntity = musicItemMapper.fromMusicItemDto(dto);
        MusicItem saved = musicItemService.save(musicItemEntity);
        return new ResponseEntity<>(musicItemMapper.toMusicItemDto(saved), HttpStatus.CREATED);
    }


    @PutMapping(path = "/music-items/{id}")
    public ResponseEntity<MusicItemDto> fullUpdateMusicItem(@PathVariable("id") Long id, @RequestBody MusicItemDto musicItemDto){
        if (!musicItemService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MusicItem musicItemEntity = musicItemMapper.fromMusicItemDto(musicItemDto);
        MusicItem saved = musicItemService.save(musicItemEntity);
        return new ResponseEntity<>(musicItemMapper.toMusicItemDto(saved), HttpStatus.OK);

    }

    @DeleteMapping(path = "/music-items/{id}")
    public ResponseEntity deleteMusicItem(@PathVariable("id") Long id){
        musicItemService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
