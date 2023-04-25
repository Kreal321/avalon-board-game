package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.util.enums.VictoryStatus;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordResponse {

    private Long recordId;
    private Long userId;
    private GameResponse game;
    private PlayerResponse current;
    private VictoryStatus victoryStatus;

}
