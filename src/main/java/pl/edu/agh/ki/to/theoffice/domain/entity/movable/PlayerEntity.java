package pl.edu.agh.ki.to.theoffice.domain.entity.movable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;

@Slf4j
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PlayerEntity extends MovableEntity {

    private ObservableMap<GamePowerup, Integer> powerups;
    private IntegerProperty lives;

    public static PlayerEntity fromProperties(GameProperties properties) {
        final PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.powerups = FXCollections.observableHashMap();
        playerEntity.powerups.putAll(GamePowerup.toMapWithDefaultValues());

        playerEntity.lives = new SimpleIntegerProperty(properties.getLives());

        return playerEntity;
    }

    public void addPowerup(GamePowerup powerup) {
        log.debug("Added powerup to player inventory: {}", powerup.name());
        this.powerups.compute(powerup, (k, v) -> v += 1);
    }

    public void removePowerup(GamePowerup powerup) {
        this.powerups.compute(powerup, (k, v) -> v -= 1);
    }

    public boolean canUsePowerup(GamePowerup powerup) {
        return this.powerups.get(powerup) > 0;
    }

    public void addLife() {
        this.lives.setValue(this.lives.get() + 1);
    }

    public void removeLife() {
        this.lives.setValue(this.lives.get() - 1);
    }

    @Override
    public void move(Location locationToMoveInto) {
        this.location = locationToMoveInto;
    }

    @Override
    public void handleCollision(long enemiesCount, long playersCount) {
        boolean playerCollidedWithEnemy = (enemiesCount > 0 && playersCount > 0);
        if (playerCollidedWithEnemy) {
            this.state = MovableEntityState.DEAD;
        }
    }

    @Override
    public EntityType getType() {
        return state == MovableEntityState.ALIVE ?
                EntityType.PLAYER :
                EntityType.DEAD_PLAYER;
    }

}
