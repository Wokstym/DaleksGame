package pl.edu.agh.ki.to.theoffice.components.game;

import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.common.component.IconProvider;
import pl.edu.agh.ki.to.theoffice.domain.entity.Entity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.EnemyEntity;
import pl.edu.agh.ki.to.theoffice.domain.entity.movable.PlayerEntity;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;

import static org.junit.jupiter.api.Assertions.*;

class BoardPaneTest {

    @Test
    void testPopulateBoard() {
        // given
        BoardPane boardPane = new BoardPane();
        LinkedMultiValueMap<Location, Entity> entities = new LinkedMultiValueMap<>();

        Location playerLocation = new Location(2, 2);
        PlayerEntity playerEntity = new PlayerEntity();
        entities.add(playerLocation, playerEntity);

        Location enemyLocation = new Location(1, 2);
        EnemyEntity enemyEntity = new EnemyEntity(new BoundedMapMoveStrategy(10, 10), enemyLocation);
        entities.add(enemyLocation, enemyEntity);

        Location emptyLocation = new Location(1, 1);

        // when
        boardPane.prepareBoard(10, 10);
        boardPane.populateBoard(new ObservableLinkedMultiValueMap(entities));

        // then
        assertEquals(IconProvider.imageOf(playerEntity), boardPane.getImages().get(playerLocation).getImage());
        assertEquals(IconProvider.imageOf(enemyEntity), boardPane.getImages().get(enemyLocation).getImage());
        assertEquals(IconProvider.EMPTY.getImage(), boardPane.getImages().get(emptyLocation).getImage());
    }
}