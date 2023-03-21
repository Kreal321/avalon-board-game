package me.kreal.avalon.util;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.dto.GameDTO;

import java.util.stream.Collectors;

public class GameMapper {

    public static GameDTO convertToDTO(Game game) {
        return GameDTO.builder()
                    .gameId(game.getGameId())
                    .gameNum(game.getGameNum())
                    .gameSize(game.getGameSize())
                    .gameStatus(game.getGameStatus())
                    .gameStartTime(game.getGameStartTime())
                    .gameEndTime(game.getGameEndTime())
                    .gameMode(game.getGameMode())
                    .players(game.getPlayers().stream()
                                .map(PlayerMapper::convertToDTO)
                                .collect(Collectors.toSet()))
                    .build();
    }

}
