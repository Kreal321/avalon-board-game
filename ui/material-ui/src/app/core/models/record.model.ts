import { Game } from "./game.model";
import { Player } from "./player.model";

export interface Record {
    recordId: number;
    userId: number;
    game: Game;
    current: Player;
}