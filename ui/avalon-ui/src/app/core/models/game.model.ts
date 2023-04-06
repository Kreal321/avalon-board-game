import { GameModeType } from '../enums/gameModeType.enum';

export interface Game {
    gameId: number;
    gameMode: GameModeType;
    gameNum: number;
    gameSize: number;
    gameStatus: string;

}