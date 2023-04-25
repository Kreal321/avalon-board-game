package me.kreal.avalon.util;

import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.Vote;
import me.kreal.avalon.dto.response.RecordResponse;
import me.kreal.avalon.dto.response.VoteResponse;

public class RecordMapper {
    public static RecordResponse convertToResponse(Record record) {
        return RecordResponse.builder()
                .recordId(record.getRecordId())
                .userId(record.getUserId())
                .game(GameMapper.convertToSimplifiedResponse(record.getGame()))
                .current(PlayerMapper.convertToResponse(record.getPlayer()))
                .victoryStatus(record.getVictoryStatus())
                .build();
    }
}
