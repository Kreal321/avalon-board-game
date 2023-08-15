package me.kreal.avalon.util.avalon.six;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class SixBasic extends GameMode {

    public SixBasic() {
        super(GameModeType.SIX_BASIC,
                new int[]{2, 3, 4, 3, 4},
                6,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.GOOD, CharacterType.GOOD, CharacterType.PERCIVAL, CharacterType.MORGANA}
        );
    }

}
