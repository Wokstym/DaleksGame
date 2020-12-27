package pl.edu.agh.ki.to.theoffice.common.component;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ImageUtils {

    public static ImageView prepareImageView(Image image, int size) {
        ImageView element = new ImageView(image);
        setSquareSize(element, size);
        return element;
    }

    public static void setSquareSize(ImageView element, int size) {
        element.setFitHeight(size);
        element.setFitWidth(size);
    }

    public static void setShadowEffect(Node button, Color shadowColor) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(shadowColor);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> button.setEffect(dropShadow));

        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> button.setEffect(null));
    }
}
