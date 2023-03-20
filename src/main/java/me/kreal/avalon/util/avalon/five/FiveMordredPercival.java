package me.kreal.avalon.util.avalon.five;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class FiveMordredPercival extends GameMode {

    public FiveMordredPercival() {
        super(GameModeType.FIVE_MORDRED_PERCIVAL,
                new int[]{2, 3, 2, 3, 3},
                5,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.PERCIVAL, CharacterType.GOOD, CharacterType.MORDRED}
        );
    }
    
}
