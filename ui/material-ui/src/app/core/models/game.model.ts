import { GameModeType } from '../enums/gameModeType.enum';
import { Character } from './character.model';
import { Player } from './player.model';

export interface Game {
    character: Character;
    gameId: number;
    gameMode: GameModeType;
    gameNum: number;
    gameSize: number;
    gameStartTime: Date;
    gameEndtime: Date;
    gameStatus: string;
    players: Player[];
}