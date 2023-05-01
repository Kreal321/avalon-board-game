package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDTO {

    private Long gameId;
    private String gameNum;
    private Integer gameSize;
    private GameStatus gameStatus;
    private Timestamp gameStartTime;
    private Timestamp gameEndTime;
    private GameModeType gameMode;

    private List<PlayerDTO> players = new ArrayList<>();

}
