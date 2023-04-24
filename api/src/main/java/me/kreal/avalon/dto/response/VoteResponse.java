package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.dto.PlayerDTO;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteResponse {

    private Long voteId;
    @JsonIgnore
    private Long roundId;
    private Long playerId;
    private Boolean accept;

}
