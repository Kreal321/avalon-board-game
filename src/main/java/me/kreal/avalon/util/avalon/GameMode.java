package me.kreal.avalon.util.avalon;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.response.CharacterInfo;
import me.kreal.avalon.util.enums.CharacterType;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.security.core.parameters.P;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GameMode {
    private final GameModeType GAME_MODE_TYPE;
    private final int[] questSize;
    private final int gameSize;
    private final CharacterType[] characterTypes;

    public GameMode(GameModeType GAME_MODE_TYPE, int[] questSize, int gameSize, CharacterType[] characterTypes) {
        this.GAME_MODE_TYPE = GAME_MODE_TYPE;
        this.questSize = questSize;
        this.gameSize = gameSize;
        this.characterTypes = characterTypes;
    }

    public GameModeType getGameMode() {
        return GAME_MODE_TYPE;
    }

    public int getGameSize() {
        return this.gameSize;
    }

    // >= 7
    public boolean isTwoFailQuest(int questNum) {
        return this.gameSize >= 7 && questNum == 4;
    }

    public int getQuestSize(int questNum) {
        return this.questSize[questNum - 1];
    }

    public void assignPlayerCharacter(Set<Player> players) {

        List<Player> playerList = players.stream().sorted((p1, p2) -> new Random().nextInt(3) - 1).collect(Collectors.toList());

        for(int idx = 0; idx < this.gameSize; idx++) {
            playerList.get(idx).setSeatNum(idx + 1);
        }

        Collections.shuffle(playerList);

        for(int idx = 0; idx < this.gameSize; idx++) {
            playerList.get(idx).setCharacterType(this.characterTypes[idx]);
        }

    }

    public CharacterInfo getCharacterInfo(Game g, Long player_id) {

        Set<Player> players = g.getPlayers();
        // assume player id existed because we can trust jwt token
        Player current = players.stream().filter(p -> p.getPlayerId().equals(player_id)).findAny().get();

        switch (current.getCharacterType()) {
            case MERLIN:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information(players.stream()
                                .filter(p -> (new HashSet<>(Arrays.asList(CharacterType.ASSASSIN, CharacterType.MORGANA, CharacterType.OBERON, CharacterType.EVIL))).contains(p.getCharacterType()))
                                .reduce("You know that ", (prev, curr) -> prev + "Seat #" + curr.getSeatNum() + ": " + curr.getDisplayName() + " is evil. ", String::concat))
                        .build();

            case GOOD:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information("You may be clueless like a penguin in the desert, but you've got heart and want to help the good guys beat this game like a pinata at a birthday party!")
                        .build();


            case EVIL:
            case MORDRED:
            case MORGANA:
            case ASSASSIN:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information(players.stream()
                                .filter(p -> (new HashSet<>(Arrays.asList(CharacterType.ASSASSIN, CharacterType.MORGANA, CharacterType.MORDRED, CharacterType.EVIL))).contains(p.getCharacterType()))
                                .reduce("You know that ", (prev, curr) -> prev + "Seat #" + curr.getSeatNum() + ": " + curr.getDisplayName() + " is your team. ", String::concat))
                        .build();

            case PERCIVAL:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information(players.stream()
                                .filter(p -> (new HashSet<>(Arrays.asList(CharacterType.MERLIN, CharacterType.MORGANA))).contains(p.getCharacterType()))
                                .reduce("You know that ", (prev, curr) -> prev + "Seat #" + curr.getSeatNum() + ": " + curr.getDisplayName() + " thumbs up. ", String::concat))
                        .build();

            case OBERON:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information("You may be clueless like a penguin in the desert, but you've got heart and want to help the bad guys beat this game like a pinata at a birthday party!")
                        .build();

            default:
                return CharacterInfo.builder()
                        .name(current.getCharacterType())
                        .information("Something went wrong.")
                        .build();
        }


    }

}
