import { Pipe, PipeTransform } from '@angular/core';
import { Player } from 'src/app/core/models/player.model';

@Pipe({name: 'player'})

export class PlayerPipe implements PipeTransform {

    constructor() {}
    
    transform(players: Player[] | undefined, playerId: number): Player | undefined {

        if (players) {
            return players.find(player => player.playerId == playerId);
        }

        return undefined;
    }
}