package me.kreal.avalon.util.avalon.seven;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class SevenBasic extends GameMode {

    public SevenBasic() {
        super(GameModeType.SEVEN_BASIC,
                new int[]{2, 3, 3, 4, 4},
                7,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.GOOD, CharacterType.GOOD, CharacterType.PERCIVAL, CharacterType.MORGANA, CharacterType.OBERON}
        );
    }

}
