package deeroot.deeroot_shop.mappers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;

public interface MusicItemMapper {
    MusicItem fromMusicItemDto(MusicItemDto musicItemDto);

    MusicItemDto toMusicItemDto(MusicItem musicItem);
}
