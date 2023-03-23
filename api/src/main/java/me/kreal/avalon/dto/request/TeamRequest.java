package me.kreal.avalon.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import me.kreal.avalon.util.enums.TeamType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {

    @NotNull(message = "Username can not be empty. ")
    private TeamType teamType;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> teamMembers;

}
