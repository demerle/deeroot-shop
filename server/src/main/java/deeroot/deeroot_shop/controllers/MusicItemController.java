package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.UserService;
import jakarta.annotation.Resource;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api")
@RestController
public class MusicItemController {

    private final UserService userService;
    MusicItemService musicItemService;

    MusicItemMapper musicItemMapper;

    public MusicItemController(MusicItemService musicItemService, MusicItemMapper musicItemMapper, UserService userService) {
        this.musicItemService = musicItemService;
        this.musicItemMapper = musicItemMapper;
        this.userService = userService;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MusicItemDto> createMusicItem(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MusicItemDto dto ){
        if (userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_ADMIN"))){

            if (musicItemService.findByFileName(dto.getFileName()) != null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }


            MusicItem musicItemEntity = musicItemMapper.fromMusicItemDto(dto);
            MusicItem saved = musicItemService.save(musicItemEntity);

            userService.updateUsersOwnedItemsWithNewItems(userDetails, List.of(saved));

            return new ResponseEntity<>(musicItemMapper.toMusicItemDto(saved), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @PutMapping(path = "/music-items/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MusicItemDto> fullUpdateMusicItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, @RequestBody MusicItemDto musicItemDto){

        if (userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_ADMIN"))) {

            if (!musicItemService.exists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            MusicItem musicItemEntity = musicItemMapper.fromMusicItemDto(musicItemDto);
            MusicItem saved = musicItemService.save(musicItemEntity);
            return new ResponseEntity<>(musicItemMapper.toMusicItemDto(saved), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /*

    @DeleteMapping(path = "/music-items/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteMusicItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id){
        if (userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_ADMIN"))) {

            musicItemService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

     */

}
