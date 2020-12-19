package pl.edu.agh.ki.to.theoffice.components.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import pl.edu.agh.ki.to.theoffice.common.component.IconProvider;
import pl.edu.agh.ki.to.theoffice.domain.entity.EntityType;
import pl.edu.agh.ki.to.theoffice.domain.map.Location;
import pl.edu.agh.ki.to.theoffice.domain.map.ObservableLinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardPaneTest {

    @Test
    void testPopulateBoard() {
        // given
        BoardPane boardPane = new BoardPane();
        LinkedMultiValueMap<Location, EntityType> entities = new LinkedMultiValueMap<>();

        Location playerLocation = new Location(2, 2);
        entities.add(playerLocation, EntityType.PLAYER);

        Location enemyLocation = new Location(1, 2);
        entities.add(enemyLocation, EntityType.ENEMY);

        Location emptyLocation = new Location(1, 1);

        // when
        boardPane.prepareBoard(10, 10);
        boardPane.populateBoard(new ObservableLinkedMultiValueMap(entities));

        // then
       /* assertEquals(IconProvider.imageOf(EntityType.PLAYER), boardPane.getImages().get(playerLocation).getImage());
        assertEquals(IconProvider.imageOf(EntityType.ENEMY), boardPane.getImages().get(enemyLocation).getImage());*/
        assertEquals(IconProvider.EMPTY.getImage(), boardPane.getImages().get(emptyLocation).getImage());
    }
}