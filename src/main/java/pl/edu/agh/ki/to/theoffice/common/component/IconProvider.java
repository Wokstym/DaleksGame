package pl.edu.agh.ki.to.theoffice.common.component;

import javafx.scene.image.Image;
import lombok.Getter;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;

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
    FRAME("frame.png"),
    REVERSE_TIME("timer.png"),
    EMPTY("empty.png");

    private static final String ICON_PATH = "/icons/";

    private final String imageSrc;
    private final Image image;

    IconProvider(String imageSrc) {
        this.imageSrc = imageSrc;
        this.image = imageOfSource();
    }

    public static Image imageOf(Entity entity) {
        var resultEnum = switch (entity.getType()) {
            case PLAYER -> PLAYER;
            case ENEMY -> ENEMY;
            case DEAD_PLAYER -> DEAD_PLAYER;
            case ENEMY_SCRAP -> ENEMY_SCRAP;
            case TELEPORT -> TELEPORT;
            case BOMB -> BOMB;
            case REVERSE_TIME -> REVERSE_TIME;
        };
        return resultEnum.image;
    }

    private Image imageOfSource() {
        return new Image(IconProvider.class.getResourceAsStream(ICON_PATH + this.imageSrc));
    }
}
