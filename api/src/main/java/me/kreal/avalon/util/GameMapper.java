package me.kreal.avalon.util;

import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.GameDTO;
import me.kreal.avalon.dto.response.CharacterInfo;
import me.kreal.avalon.dto.response.GameResponse;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameStatus;
import me.kreal.avalon.util.enums.TeamMemberStatus;

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
                                .collect(Collectors.toList()))
                    .build();
    }

    public static GameResponse convertToSimplifiedResponse(Game game) {
        return GameResponse.builder()
                .gameId(game.getGameId())
                .gameNum(game.getGameNum())
                .gameSize(game.getGameSize())
                .gameStatus(game.getGameStatus())
                .gameStartTime(game.getGameStartTime())
                .gameEndTime(game.getGameEndTime())
                .gameMode(game.getGameMode())
                .build();
    }


    public static GameResponse convertToResponse(Game game) {
        GameResponse response = GameResponse.builder()
                    .gameId(game.getGameId())
                    .gameNum(game.getGameNum())
                    .gameSize(game.getGameSize())
                    .gameStatus(game.getGameStatus())
                    .gameStartTime(game.getGameStartTime())
                    .gameEndTime(game.getGameEndTime())
                    .gameMode(game.getGameMode())
                    .players(game.getPlayers().stream()
                                .map(PlayerMapper::convertToResponse)
                                .collect(Collectors.toList()))
                    .rounds(game.getRounds().stream()
                                .map(RoundMapper::convertToResponse)
                                .collect(Collectors.toList()))
                    .build();

        switch (game.getGameStatus()) {
            case IN_PROGRESS:
                response.getPlayers()
                        .forEach(player -> player.setCharacterType(null));
                response.getRounds().stream()
                        .flatMap(round -> round.getTeams().stream())
                        .flatMap(team -> team.getTeamMembers().stream())
                        .filter(teamMember -> teamMember.getStatus() != TeamMemberStatus.MISSION_PENDING)
                        .forEach(teamMember -> teamMember.setStatus(null));
                break;
        }

        return response;
    }

    public static GameResponse convertToResponse(Game game, Long player_id) {
        GameResponse response = GameMapper.convertToResponse(game);

        response.setCharacter(GameModeFactory.getGameMode(game.getGameMode()).getCharacterInfo(game, player_id));

        return response;
    }

    public static GameResponse convertToResponse(Game game, Player player) {
        GameResponse response = GameMapper.convertToResponse(game);

        response.setCharacter(
                CharacterInfo.builder()
                        .current(PlayerMapper.convertToResponse(player))
                        .build()
        );
        return response;
    }

}
