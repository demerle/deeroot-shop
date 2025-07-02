package deeroot.deeroot_shop.mappers.impl;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicItemMapperImpl implements MusicItemMapper {

    private final ModelMapper modelMapper;

    public MusicItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MusicItem fromMusicItemDto(MusicItemDto dto) {
        return modelMapper.map(dto, MusicItem.class);

        /*
        return new MusicItem(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.composer(),
                dto.price(),
                dto.fileName(),
                dto.fileType(),
                dto.imageFileName(),
                dto.imageFileType(),
                dto.s3Key(),
                dto.s3Url()
        );
         */
    }

    @Override
    public MusicItemDto toMusicItemDto(MusicItem musicItem) {

        return modelMapper.map(musicItem, MusicItemDto.class);
       /*
        return new MusicItemDto(
                musicItem.getId(),
                musicItem.getTitle(),
                musicItem.getDescription(),
                musicItem.getComposer(),
                musicItem.getPrice(),
                musicItem.getFileName(),
                musicItem.getFileType(),
                musicItem.getImageFileName(),
                musicItem.getImageFileType(),
                musicItem.getS3Key(),
                musicItem.getS3Url()
        );

        */
    }
}
