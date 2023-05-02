import { CharacterType } from '../enums/characterType.enum';

export interface Player {
    playerId: number;
    displayName: string;
    characterType: CharacterType;
    seatNum: number;
    isAssassinated: boolean;
}