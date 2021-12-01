package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.ImageDto;
import hr.trio.realestatetracker.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageConverter implements Converter<Image, ImageDto> {

    @Override
    public ImageDto convert(final Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .path(image.getPath())
                .build();
    }

    public Image convert(final ImageDto imageDto) {
        return Image.builder()
                .id(imageDto.getId())
                .path(imageDto.getPath())
                .build();
    }

}
