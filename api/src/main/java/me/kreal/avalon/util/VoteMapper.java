package me.kreal.avalon.util;

import me.kreal.avalon.domain.Vote;
import me.kreal.avalon.dto.response.VoteResponse;

import java.util.List;

public class VoteMapper {
    public static VoteResponse convertToResponse(Vote vote) {
        return VoteResponse.builder()
                .voteId(vote.getVoteId())
                .roundId(vote.getRound().getRoundId())
                .playerId(vote.getPlayerId())
                .accept(vote.getAccept())
                .build();
    }
}
