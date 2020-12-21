package pl.edu.agh.ki.to.theoffice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import pl.edu.agh.ki.to.theoffice.domain.game.properties.GameMapProperties;
import pl.edu.agh.ki.to.theoffice.domain.map.move.BoundedMapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.MapMoveStrategy;
import pl.edu.agh.ki.to.theoffice.domain.map.move.WrappedMapMoveStrategy;

@Configuration
@PropertySources({
        @PropertySource("classpath:/application.yml"),
        @PropertySource("classpath:/config.properties")
})
public class AppConfig {

    @Value("${map.width:20}")
    private int mapWidth;

    @Value("${map.height:20}")
    private int mapHeight;

    @Bean
    public GameMapProperties gameMapProperties() {
        return new GameMapProperties(mapWidth, mapHeight);
    }

    @Bean
    @ConditionalOnProperty(value = "map.strategy", havingValue = "bounded", matchIfMissing = true)
    public MapMoveStrategy boundedMapMoveStrategy() {
        return new BoundedMapMoveStrategy(mapWidth, mapHeight);
    }

    @Bean
    @ConditionalOnProperty(value = "map.strategy", havingValue = "wrapped")
    public MapMoveStrategy wrappedMapMoveStrategy() {
        return new WrappedMapMoveStrategy(mapWidth, mapHeight);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
