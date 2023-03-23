package me.kreal.avalon.util.enums;

public enum CharacterType {
    MERLIN, ASSASSIN, PERCIVAL, MORGANA, MORDRED, OBERON, GOOD, EVIL;

    public static boolean isEvil(CharacterType characterType) {
        return characterType == ASSASSIN || characterType == MORDRED || characterType == MORGANA || characterType == OBERON || characterType == EVIL;
    }

    public static boolean isGood(CharacterType characterType) {
        return characterType == MERLIN || characterType == PERCIVAL || characterType == GOOD;
    }

}
