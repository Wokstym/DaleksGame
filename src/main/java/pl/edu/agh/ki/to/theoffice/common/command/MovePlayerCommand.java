package pl.edu.agh.ki.to.theoffice.common.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.MovableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MovePlayerCommand implements Command {

    private final Game game;
    private final Location.Direction direction;
    private final List<MovableChange> movableChanges;
    private final List<PickableChange> pickableChanges;
    private final int score;

    public MovePlayerCommand(Game game, Location.Direction direction) {
        this.game = game;
        this.direction = direction;

        movableChanges = game.getEntities().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(MovableEntity.class::isInstance)
                .map(MovableEntity.class::cast)
                .map(entity -> new MovableChange(entity, entity.getLocation(), entity.getState()))
                .collect(Collectors.toList());

        pickableChanges = game.getEntities().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(PickableEntity.class::isInstance)
                .map(PickableEntity.class::cast)
                .map(entity -> new PickableChange(entity, entity.getLocation()))
                .collect(Collectors.toList());
        score = game.getScore().get();

    }

    @Override
    public void execute() {
        game.movePlayer(direction);

    }

    @Override
    public void undo() {
        var entities = game.getEntities();
        entities.clear();
        handleMovableEntities(entities);
        handlePickableEntities(entities);

        PlayerEntity playerEntity = game.getPlayerEntity();
        game.getPlayerLocation().setValue(playerEntity.getLocation());
        game.getScore().setValue(score);
    }

    private void handleMovableEntities(ObservableLinkedMultiValueMap<Location, Entity> entities) {
        for (MovableChange movableChange : movableChanges) {
            movableChange.entity.setLocation(movableChange.before);
            entities.add(movableChange.before, movableChange.entity);
            movableChange.entity.setState(movableChange.stateBefore);
        }
    }

    private void handlePickableEntities(ObservableLinkedMultiValueMap<Location, Entity> entities) {
        Map<Location, PickableEntity> powerupsOnMap = new HashMap<>();
        for (PickableChange pickableChange : pickableChanges) {
            pickableChange.entity.setLocation(pickableChange.before);
            entities.add(pickableChange.before, pickableChange.entity);
            powerupsOnMap.put(pickableChange.before, pickableChange.entity);
        }
        game.setPowerupsOnMap(powerupsOnMap);
    }

    @Getter
    @RequiredArgsConstructor
    static class MovableChange {
        private final MovableEntity entity;
        private final Location before;
        private final MovableEntity.MovableEntityState stateBefore;

    }

    @Getter
    @RequiredArgsConstructor
    static class PickableChange {
        private final PickableEntity entity;
        private final Location before;

    }
}
