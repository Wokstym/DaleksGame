package pl.edu.agh.ki.to.theoffice.domain.game;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class GameManager {

    @Getter
    @NonNull
    private Game game;

    @Autowired
    public GameManager() {

    }

}
