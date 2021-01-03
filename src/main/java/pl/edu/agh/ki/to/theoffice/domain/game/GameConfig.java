package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GamePropertiesConfiguration;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.MapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.WrappedMapMoveStrategy;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class GameConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ConfigurationProperties(prefix = "game.map")
    public MapProperties mapProperties() {
        return new MapProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "game")
    public GamePropertiesConfiguration gamePropertiesConfiguration() {
        return new GamePropertiesConfiguration();
    }

    @Bean
    @ConditionalOnProperty(value = "game.map.strategy", havingValue = "bounded", matchIfMissing = true)
    public MapMoveStrategy boundedMapMoveStrategy() {
        final MapProperties mapProperties = mapProperties();
        return new BoundedMapMoveStrategy(mapProperties.getWidth(), mapProperties.getHeight());
    }

    @Bean
    @ConditionalOnProperty(value = "game.map.strategy", havingValue = "wrapped")
    public MapMoveStrategy wrappedMapMoveStrategy() {
        final MapProperties mapProperties = mapProperties();
        return new WrappedMapMoveStrategy(mapProperties.getWidth(), mapProperties.getHeight());
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("game properties configurations: {}", gamePropertiesConfiguration());
        log.debug("map properties: {}", mapProperties());
        var config = gamePropertiesConfiguration();
        log.debug("keyset: {}", config.getConfigurationsByDifficulty().keySet());
    }

}
