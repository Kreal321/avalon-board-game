package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.util.enums.CharacterType;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDTO {

    private Long playerId;
    private String displayName;
    private CharacterType characterType;
    private Integer seatNum;

}
