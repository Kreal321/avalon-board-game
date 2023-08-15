package me.kreal.avalon.util.avalon.nine;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class NineBasic extends GameMode {

    public NineBasic() {
        super(GameModeType.NINE_BASIC,
                new int[]{3, 4, 4, 5, 5},
                9,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.GOOD, CharacterType.GOOD, CharacterType.GOOD, CharacterType.GOOD, CharacterType.PERCIVAL, CharacterType.MORGANA, CharacterType.MORDRED}
        );
    }

}
