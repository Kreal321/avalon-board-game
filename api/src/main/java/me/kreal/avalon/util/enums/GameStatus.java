package me.kreal.avalon.util.enums;

public enum GameStatus {
    NOT_STARTED, IN_PROGRESS, EVIL_WINS, GOOD_WINS;

    public static boolean gameIsFinished(GameStatus status) {
        return status == EVIL_WINS || status == GOOD_WINS;
    }

}

