package me.kreal.avalon.util.enums;

import me.kreal.avalon.domain.Round;

public enum RoundStatus {
    INITIAL_TEAM, DISCUSSING, FINAL_TEAM_VOTING, FINAL_TEAM_VOTING_SUCCESS, FINAL_TEAM_VOTING_FAIL, QUEST_SUCCESS, QUEST_FAIL;

    public static boolean roundIsFinished(RoundStatus status) {
        return status == FINAL_TEAM_VOTING_FAIL || status == QUEST_SUCCESS || status == QUEST_FAIL;
    }

    public static boolean roundHasQuest(Round round) {
        return round.getRoundStatus() == QUEST_SUCCESS || round.getRoundStatus() == QUEST_FAIL;
    }
}
