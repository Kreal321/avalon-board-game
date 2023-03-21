package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import me.kreal.avalon.util.enums.CharacterType;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterInfo {

    private CharacterType name;
    private String information;

}
