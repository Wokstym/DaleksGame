package pl.edu.agh.ki.to.theoffice.service;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.pickable.PickableEntityFactory;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameState;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameMapProperties;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Setter
public class GameFromPropertiesService {

    @Autowired
    private GameMapProperties gameMapProperties;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // todo check if solution is fine, strange warning
    private MapMoveStrategy mapMoveStrategy;

    public Game fromProperties(GameProperties properties) {

        var entities = generateEnemies(properties);

        var playerEntity = generatePlayer(entities, properties.getPlayerProperties());
        entities.add(playerEntity.getLocation(), playerEntity);

        var powerupsOnMap = generatePowerups(entities, properties.getPlayerProperties());
        powerupsOnMap.forEach(entities::add);

        return Game.builder()
                .gameProperties(properties)
                .mapMoveStrategy(mapMoveStrategy)
                .gameState(new SimpleObjectProperty<>(GameState.IN_PROGRESS))
                .entities(new ObservableLinkedMultiValueMap<>(entities))
                .playerEntity(playerEntity)
                .playerLocation(new SimpleObjectProperty<>(playerEntity.getLocation()))
                .powerupsOnMap(powerupsOnMap)
                .build();
    }

    private LinkedMultiValueMap<Location, Entity> generateEnemies(GameProperties properties) {
        final var entities = new LinkedMultiValueMap<Location, Entity>();
        while (entities.size() < properties.getEnemies()) {
            Location location = Location.randomLocation(
                    gameMapProperties.getWidth(),
                    gameMapProperties.getHeight()
            );
            entities.addIfAbsent(
                    location,
                    new EnemyEntity(mapMoveStrategy, location));
        }
        return entities;
    }

    private PlayerEntity generatePlayer(LinkedMultiValueMap<Location, Entity> entities, GameProperties.GamePlayerProperties playerProperties) {
        PlayerEntity player = PlayerEntity.fromProperties(playerProperties);
        Location playerLocation;
        do {
            playerLocation = Location.randomLocation(gameMapProperties.getWidth(), gameMapProperties.getHeight());
        } while (entities.containsKey(playerLocation));
        player.setLocation(playerLocation);
        return player;
    }

    private Map<Location, PickableEntity> generatePowerups(LinkedMultiValueMap<Location, Entity> entities, GameProperties.GamePlayerProperties playerProperties) {

        return playerProperties.getPowerups().entrySet().stream()
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(nr -> e.getKey()))
                .map(e -> {
                    Location powerupLocation;
                    do {
                        powerupLocation = Location.randomLocation(gameMapProperties.getWidth(), gameMapProperties.getHeight());
                    } while (entities.containsKey(powerupLocation));
                    return new AbstractMap.SimpleEntry<>(powerupLocation, PickableEntityFactory.fromEntityType(e));
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

}
