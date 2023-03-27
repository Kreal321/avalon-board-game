package me.kreal.avalon.util.enums;

public enum GameStatus {
    NOT_STARTED, IN_PROGRESS, ASSASSIN_FLOP, EVIL_WON_WITH_ASSASSINATION, EVIL_WON_WITH_QUEST, GOOD_WON;

    public static boolean gameIsFinished(GameStatus status) {
        return status == EVIL_WON_WITH_ASSASSINATION || status == EVIL_WON_WITH_QUEST || status == GOOD_WON;
    }

    public static boolean assassinIsInAction(GameStatus status) {
        return status == ASSASSIN_FLOP;
    }

}

