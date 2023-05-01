import { GameModeType } from '../enums/gameModeType.enum';
import { GameStatus } from '../enums/gameStatus.enum';
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
    gameEndTime: Date;
    gameStatus: GameStatus;
    players: Player[];
    rounds: Round[];
}