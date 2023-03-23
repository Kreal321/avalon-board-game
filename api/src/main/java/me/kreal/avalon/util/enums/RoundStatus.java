package me.kreal.avalon.util.enums;

public enum RoundStatus {
    INITIAL_TEAM, DISCUSSING, FINAL_TEAM_VOTING, FINAL_TEAM_VOTING_SUCCESS, FINAL_TEAM_VOTING_FAIL, QUEST_SUCCESS, QUEST_FAIL;

    public static boolean roundIsFinished(RoundStatus status) {
        return status == FINAL_TEAM_VOTING_FAIL || status == QUEST_SUCCESS || status == QUEST_FAIL;
    }
}
