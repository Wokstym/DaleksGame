package pl.edu.agh.ki.to.theoffice.components.game;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.ki.to.theoffice.common.component.IconProvider;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.HashMap;
import java.util.List;

import static pl.edu.agh.ki.to.theoffice.common.component.ImageUtils.prepareImageView;

@Slf4j
@Getter
public class BoardPane extends TilePane implements MapChangeListener<Location, List<Entity>> {

    private static final double MAP_SIZE = 750.0D;

    private final HashMap<Location, ImageView> images = new HashMap<>();
    private int columns;
    private int rows;
    private int elementSize;

    public void prepareBoard(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.elementSize = (int) (MAP_SIZE / Math.max(columns, rows));

        setBoardSize(columns, rows);
        setBackground();
    }

    private void setBoardSize(int columns, int rows) {
        int pixelWidth = columns * this.elementSize;
        int pixelHeight = rows * this.elementSize;

        setMaxWidth(pixelWidth);
        setMaxHeight(pixelHeight);
        setMinWidth(pixelWidth);
        setMinHeight(pixelHeight);
    }

    private void setBackground() {
        Image img = IconProvider.FRAME.getImage();
        BackgroundImage bgImg = new BackgroundImage(
                img,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(this.elementSize * 2, this.elementSize * 2, false, false, false, false)
        );
        setBackground(new Background(bgImg));
    }

    public void populateBoard(ObservableLinkedMultiValueMap<Location, Entity> entities) {
        getChildren().clear();

        for (Location location : Location.generateLocationsWithinBoundsWithRespectOfLeftBottomCorner(0, columns, 0, rows)) {
            List<Entity> entityTypes = entities.get(location);

            Image image = CollectionUtils.isEmpty(entityTypes) ? IconProvider.EMPTY.getImage() : IconProvider.imageOf(entityTypes.get(0));
            ImageView element = prepareImageView(image, this.elementSize);

            getChildren().add(element);
            images.put(location, element);
        }
    }

    @Override
    public void onChanged(MapChangeListener.Change<? extends Location, ? extends List<Entity>> change) {
        ImageView imageAtChangedPosition = images.get(change.getKey());

        if (change.wasRemoved()) {
            Image image = IconProvider.EMPTY.getImage();
            imageAtChangedPosition.setImage(image);
        }

        if (change.wasAdded()) {
            log.debug("Entity added: {}", change.getValueAdded().get(0).getType());
            Image image = IconProvider.imageOf(change.getValueAdded().get(0));
            imageAtChangedPosition.setImage(image);
        }
    }

}
