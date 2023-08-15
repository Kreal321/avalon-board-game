package me.kreal.avalon;

import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.avalon.eight.EightBasic;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import me.kreal.avalon.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class GameModeTest {

    private final List<Player> playerList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        for(int idx = 0; idx < 8; idx++) {
            Player player = new Player();
            player.setDisplayName("Player" + (idx + 1));
            this.playerList.add(player);
        }
        this.playerList.get(0).setCharacterType(CharacterType.GOOD);
        this.playerList.get(1).setCharacterType(CharacterType.GOOD);
        this.playerList.get(2).setCharacterType(CharacterType.GOOD);
        this.playerList.get(3).setCharacterType(CharacterType.EVIL);
        this.playerList.get(4).setCharacterType(CharacterType.MORGANA);
        this.playerList.get(5).setCharacterType(CharacterType.PERCIVAL);
        this.playerList.get(6).setCharacterType(CharacterType.MERLIN);
        this.playerList.get(7).setCharacterType(CharacterType.MERLIN);
    }

    @Test
    public void test() {
        EightBasic eightBasic = new EightBasic();
        eightBasic.assignPlayerCharacter(this.playerList);
        this.playerList.forEach(player -> System.out.println(player.getDisplayName() +" " + player.getSeatNum() + " " + player.getCharacterType()));
    }
}
