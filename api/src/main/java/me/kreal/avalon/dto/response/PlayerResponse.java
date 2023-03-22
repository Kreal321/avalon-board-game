package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.util.enums.CharacterType;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerResponse {

    private Long playerId;
    private String displayName;
    private CharacterType characterType;
    private Integer seatNum;

}
