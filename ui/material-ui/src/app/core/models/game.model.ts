import { GameModeType } from '../enums/gameModeType.enum';
import { Character } from './character.model';
import { Player } from './player.model';
import { Round } from './round.model';

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
    rounds: Round[];
}