package pl.edu.agh.ki.to.theoffice.domain.map;

public enum EntityType {

    PLAYER,
    ENEMY,
    ENEMY_SCRAP,
    DEAD_PLAYER;

    public boolean isPlayerRelated() {
        return this == PLAYER || this == DEAD_PLAYER;
    }

    public boolean isEnemyRelated() {
        return this == ENEMY || this == ENEMY_SCRAP;
    }

}
