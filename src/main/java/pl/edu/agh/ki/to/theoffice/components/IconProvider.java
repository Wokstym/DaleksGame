package pl.edu.agh.ki.to.theoffice.components;

import javafx.scene.image.Image;
import lombok.Getter;
import pl.edu.agh.ki.to.theoffice.domain.map.EntityType;

@Getter
public enum IconProvider {
    BOMB("bomb.png"),
    DEAD_PLAYER("dead_player.png"),
    ENEMY("enemy.png"),
    ENEMY_SCRAP("enemy_scrap.png"),
    HEARTH("hearth.png"),
    PLAYER("player.png"),
    RESTART("restart.png"),
    TELEPORT("teleport.png"),
    DOT("dot.png"),
    ARROW("arrow.png"),
    EMPTY("empty.png");

    private static final String ICON_PATH = "/icons/";

    private final String imageSrc;
    private final Image image;

    IconProvider(String imageSrc) {
        this.imageSrc = imageSrc;
        this.image = imageOfSource();
    }

    public static Image imageOf(EntityType entityType) {
        var resultEnum = switch (entityType) {
            case PLAYER -> PLAYER;
            case ENEMY -> ENEMY;
            case DEAD_PLAYER -> DEAD_PLAYER;
            case ENEMY_SCRAP -> ENEMY_SCRAP;
        };
        return resultEnum.image;
    }

    private Image imageOfSource() {
        return new Image(IconProvider.class.getResourceAsStream(ICON_PATH + this.imageSrc));
    }
}
