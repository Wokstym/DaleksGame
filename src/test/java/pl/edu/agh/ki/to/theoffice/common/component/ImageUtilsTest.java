package pl.edu.agh.ki.to.theoffice.common.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ImageUtilsTest {

    @Test
    void testPrepareImageView() {
        // given
        Image image = IconProvider.PLAYER.getImage();
        int size = 20;

        // when
        ImageView imageView = ImageUtils.prepareImageView(image, size);

        // then
        assertEquals(size, imageView.getFitWidth());
        assertEquals(size, imageView.getFitHeight());
        assertEquals(image, imageView.getImage());
    }
}