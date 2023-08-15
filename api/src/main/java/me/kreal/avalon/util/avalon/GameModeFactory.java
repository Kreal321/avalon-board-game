package me.kreal.avalon.util.avalon;

import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameModeFactory {

    private final List<GameMode> gameModes;
    private static final Map<GameModeType, GameMode> gameModeCache = new HashMap<>();

    @Autowired
    public GameModeFactory(List<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    @PostConstruct
    public void initGameModeCache() {
        this.gameModes.forEach(gameMode -> gameModeCache.put(gameMode.getGameMode(), gameMode));
    }

    public static GameMode getGameMode(GameModeType gameModeType) {
        return gameModeCache.get(gameModeType);
    }

}
