package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import me.kreal.avalon.dto.PlayerDTO;
import me.kreal.avalon.dto.RoundDTO;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse {

    private Long gameId;
    private int gameNum;
    private Integer gameSize;
    private GameStatus gameStatus;
    private Timestamp gameStartTime;
    private Timestamp gameEndTime;
    private GameModeType gameMode;

    private List<PlayerResponse> players = new ArrayList<>();
    private List<RoundResponse> rounds = new ArrayList<>();

    private CharacterInfo character;

}
