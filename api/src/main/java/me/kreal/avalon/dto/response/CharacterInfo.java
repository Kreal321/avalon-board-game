package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.PlayerDTO;
import me.kreal.avalon.util.enums.CharacterType;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterInfo {

    private CharacterType characterType;
    private Player current;
    private String information;
    private List<PlayerDTO> thumbsUpPlayers;

}
