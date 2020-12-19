package pl.edu.agh.ki.to.theoffice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import pl.edu.agh.ki.to.theoffice.domain.entity.GamePowerup;
import pl.edu.agh.ki.to.theoffice.domain.game.Game;
import pl.edu.agh.ki.to.theoffice.domain.game.GameDifficulty;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameProperties;
import pl.edu.agh.ki.to.theoffice.service.GameFromDifficultyService;
import pl.edu.agh.ki.to.theoffice.service.GameFromPropertiesService;

@Configuration
@PropertySource("classpath:/config.properties")
public class GameConfig {

    static class GameFromDifficultyConfig {

        @Value("${game.difficulty:easy}")
        private GameDifficulty gameDifficulty;

        @Autowired
        private GameFromDifficultyService gameFromDifficultyService;

        @Bean
        @ConditionalOnProperty(value = "game.difficulty")
        public Game gameFromDifficulty() {
            return gameFromDifficultyService.fromDifficultyType(gameDifficulty);
        }
    }

    static class GameFromPropertiesConfig {

        @Value("${player.teleports:0}")
        private int teleport;

        @Value("${player.bombs:0}")
        private int bomb;

        @Value("${game.enemies:0}")
        private int enemies;

        @Value("${player.lives}")
        private int lives;

        @Autowired
        private GameFromPropertiesService gameFromPropertiesService;

        @Bean
        @Conditional(NoDifficultyDefinedCondition.class)
        public Game gameFromProperties() {
            var gamePlayerProperties = GameProperties.GamePlayerProperties.builder()
                    .powerup(GamePowerup.BOMB, bomb)
                    .powerup(GamePowerup.TELEPORT, teleport)
                    .lives(lives)
                    .build();

            GameProperties gameProperties = GameProperties.builder()
                    .enemies(enemies)
                    .playerProperties(gamePlayerProperties)
                    .build();

            return gameFromPropertiesService.fromProperties(gameProperties);
        }

        static class NoDifficultyDefinedCondition implements Condition {

            @Override
            public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
                Environment env = context.getEnvironment();
                String gameDifficulty = env.getProperty("game.difficulty");
                return gameDifficulty == null;
            }
        }
    }
}
