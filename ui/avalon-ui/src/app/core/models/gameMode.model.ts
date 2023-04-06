import { GameModeType } from "../enums/gameModeType.enum";

export interface GameMode {
    id: number;
    tabName: string;
    name: string;
    rules: string;
    description: string;
    gameModeType: GameModeType;
    numberOfPlayers: number;
}