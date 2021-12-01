package hr.trio.realestatetracker.converter;

import hr.trio.realestatetracker.dto.ImageDto;
import hr.trio.realestatetracker.model.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ImageConverterTest {

    @InjectMocks
    private ImageConverter imageConverter;

    @Test
    public void shouldConvertImageToImageDto() {
        final Image image = Image.builder().id(2L).path("path").build();
        final ImageDto expectedDto = ImageDto.builder().id(2L).path("path").build();

        assertThat(imageConverter.convert(image)).isEqualTo(expectedDto);
    }

    @Test
    public void shouldConvertImageDtoToImage() {
        final ImageDto imageDto = ImageDto.builder().id(2L).path("path").build();
        final Image expectedImage = Image.builder().id(2L).path("path").build();

        assertThat(imageConverter.convert(imageDto)).isEqualTo(expectedImage);
    }

}