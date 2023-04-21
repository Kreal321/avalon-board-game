import { GameModeType } from "../enums/gameModeType.enum";

export interface GameMode {
    id: number;
    quests: number[];
    name: string;
    rules: string;
    description: string;
    gameModeType: GameModeType;
    numberOfPlayers: number;
}