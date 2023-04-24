package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import me.kreal.avalon.dto.PlayerDTO;
import me.kreal.avalon.dto.TeamDTO;
import me.kreal.avalon.dto.VoteDTO;
import me.kreal.avalon.util.enums.RoundStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundResponse {

    private Long roundId;
    private Long gameId;
    private Integer questNum;
    private Integer roundNum;
    @JsonIgnore
    private Long leaderId;
    private PlayerResponse leader;
    private Integer teamSize;
    private RoundStatus roundStatus;

    private List<VoteResponse> votes = new ArrayList<>();
    private List<TeamResponse> teams = new ArrayList<>();


}
