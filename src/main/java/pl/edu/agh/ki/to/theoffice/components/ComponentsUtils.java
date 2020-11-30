package pl.edu.agh.ki.to.theoffice.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ComponentsUtils {

    public static ImageView prepareImageView(Image image, int size) {
        ImageView element = new ImageView(image);
        setSquareSize(element, size);
        return element;
    }

    public static void setSquareSize(ImageView element, int size) {
        element.setFitHeight(size);
        element.setFitWidth(size);
    }
}
