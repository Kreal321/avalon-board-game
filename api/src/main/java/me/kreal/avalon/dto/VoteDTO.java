package me.kreal.avalon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Round;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteDTO {

    private Long voteId;
    private Long roundId;
    private PlayerDTO player;
    private Boolean accept;

}
