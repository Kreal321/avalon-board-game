package me.kreal.avalon.util.avalon.ten;

import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.stereotype.Component;

@Component
public class TenBasic extends GameMode {

    public TenBasic() {
        super(GameModeType.TEN_BASIC,
                new int[]{3, 4, 4, 5, 5},
                10,
                new CharacterType[]{CharacterType.MERLIN, CharacterType.ASSASSIN, CharacterType.GOOD, CharacterType.GOOD, CharacterType.GOOD, CharacterType.GOOD, CharacterType.PERCIVAL, CharacterType.MORGANA, CharacterType.MORDRED, CharacterType.OBERON}
        );
    }

}
