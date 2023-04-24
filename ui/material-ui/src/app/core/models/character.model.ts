import { CharacterType } from "../enums/characterType.enum";
import { Player } from "./player.model";

export interface Character {
    characterType: CharacterType;
    current: Player;
    thumbsUpPlayers: Player[];
}