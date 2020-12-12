package pl.edu.agh.ki.to.theoffice.domain.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {

    PLAYER(1),
    ENEMY(3),
    ENEMY_SCRAP(2),
    DEAD_PLAYER(0);

    private final int mapPriority;

    public boolean isPlayerRelated() {
        return this == PLAYER || this == DEAD_PLAYER;
    }

    public boolean isEnemyRelated() {
        return this == ENEMY || this == ENEMY_SCRAP;
    }

}
