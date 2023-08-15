package me.kreal.avalon.util.avalon.eight;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class EightBasic extends GameMode {

    public EightBasic() {
        super(GameModeType.EIGHT_BASIC,
                new int[]{3, 4, 4, 5, 5},
                8,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.GOOD, CharacterType.GOOD, CharacterType.GOOD, CharacterType.PERCIVAL, CharacterType.MORGANA, CharacterType.EVIL}
        );
    }

}
