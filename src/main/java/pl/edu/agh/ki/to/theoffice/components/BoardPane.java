package pl.edu.agh.ki.to.theoffice.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.HashMap;
import java.util.List;

import static pl.edu.agh.ki.to.theoffice.components.ComponentsUtils.prepareImageView;

public class BoardPane extends TilePane {

    private static final int ELEMENT_SIZE = 40;

    private final HashMap<Location, ImageView> images = new HashMap<>();
    private int columnsNr;
    private int rowsNr;

    public void setBoardSize(int columnsNr, int rowsNr) {
        this.columnsNr = columnsNr;
        this.rowsNr = rowsNr;

        int pixelWidth = columnsNr * ELEMENT_SIZE;
        int pixelHeight = rowsNr * ELEMENT_SIZE;

        setMaxWidth(pixelWidth);
        setMaxHeight(pixelHeight);
        setMinWidth(pixelWidth);
        setMinHeight(pixelHeight);
    }

    public void populateBoard(ObservableLinkedMultiValueMap<Location, EntityType> entities) {

        for (Location location : Location.generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(0, columnsNr, 0, rowsNr)) {
            List<EntityType> entityTypes = entities.get(location);

            Image image = CollectionUtils.isEmpty(entityTypes) ? IconProvider.EMPTY.getImage() : IconProvider.imageOf(entityTypes.get(0));
            ImageView element = prepareImageView(image, ELEMENT_SIZE);

            getChildren().add(element);
            images.put(location, element);
        }
    }

    public ImageView getImageViewAt(Location location){
        return images.get(location);
    }
}
