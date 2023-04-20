import { CharacterType } from "../enums/characterType.enum";
import { Player } from "./player.model";

export interface Character {
    characterType: CharacterType;
    thumbsUpPlayers: Player[];
}