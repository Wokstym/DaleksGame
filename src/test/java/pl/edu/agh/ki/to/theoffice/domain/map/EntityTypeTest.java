package pl.edu.agh.ki.to.theoffice.domain.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntityTypeTest {

    @Test
    public void testIsPlayerRelated() {
        // given
        EntityType entityType = EntityType.PLAYER;

        // when then
        assertTrue(entityType.isPlayerRelated());
        assertFalse(entityType.isEnemyRelated());
    }

    @Test
    public void testIsPlayerRelatedWhenDead() {
        // given
        EntityType entityType = EntityType.DEAD_PLAYER;

        // when then
        assertTrue(entityType.isPlayerRelated());
        assertFalse(entityType.isEnemyRelated());
    }

    @Test
    public void testisEnemyRelated() {
        // given
        EntityType entityType = EntityType.ENEMY;

        // when then
        assertTrue(entityType.isEnemyRelated());
        assertFalse(entityType.isPlayerRelated());
    }

    @Test
    public void testisEnemyRelatedWhenScrap() {
        // given
        EntityType entityType = EntityType.ENEMY_SCRAP;

        // when then
        assertTrue(entityType.isEnemyRelated());
        assertFalse(entityType.isPlayerRelated());
    }
}