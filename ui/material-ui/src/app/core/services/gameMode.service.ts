import { Injectable, OnInit } from '@angular/core';
import { GameMode } from '../models/gameMode.model';
import { Quest } from '../models/quest.model';
import { GameModeType } from '../enums/gameModeType.enum';
import { Observable, of } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class GameModeService implements OnInit {

    private gameModes : GameMode[] = [
        { id: 51, name: "5 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 5, quests: [2, 3, 2, 3, 3], description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 52, name: "5 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, quests: [2, 3, 2, 3, 3],  numberOfPlayers: 5, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 53, name: "5 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, quests: [2, 3, 2, 3, 3],  numberOfPlayers: 5, description: "Easily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 61, name: "6 Players - Basic", gameModeType: GameModeType.SIX_BASIC, numberOfPlayers: 6, quests: [2, 3, 4, 3, 4],  description: "The standard setup, seems to be best for beginners.", rules: "Merlin, Percival, 2 generic good guys VS Assassin, Morgana." },
        { id: 71, name: "7 Players - Basic", gameModeType: GameModeType.SEVEN_BASIC, numberOfPlayers: 7, quests: [2, 3, 3, 4, 4], description: "The standard setup, seems to be best for beginners.", rules: "Merlin, Percival, 2 generic good guys VS Assassin, Morgana, Oberon." },
        { id: 81, name: "8 Players - Basic", gameModeType: GameModeType.EIGHT_BASIC, numberOfPlayers: 8, quests: [3, 4, 4, 5, 5], description: "The standard setup, seems to be best for beginners.", rules: "Merlin, Percival, 3 generic good guys VS Assassin, Morgana, generic bad guy." },
        { id: 91, name: "9 Players - Basic", gameModeType: GameModeType.NINE_BASIC, numberOfPlayers: 9, quests: [3, 4, 4, 5, 5], description: "The standard setup, seems to be best for beginners.", rules: "Merlin, Percival, 4 generic good guys VS Assassin, Morgana, Mordred" },
        { id: 101, name: "10 Players - Basic", gameModeType: GameModeType.TEN_BASIC, numberOfPlayers: 10, quests: [3, 4, 4, 5, 5], description: "The standard setup, seems to be best for beginners.", rules: "Merlin, Percival, 4 generic good guys VS Assassin, Morgana, Mordred, Oberon" },

    ];

    constructor() { }

    ngOnInit() {

    }

    getGameModes() : Observable<GameMode[]> {
        return of(this.gameModes);
    }

    getGameModeById(id: number) : Observable<GameMode | undefined> {
        return of(this.gameModes.find(gameMode => gameMode.id == id));
    }

    getGameModeByGameModeType(gameModeType: GameModeType) : Observable<GameMode | undefined> {
        return of(this.gameModes.find(gameMode => gameMode.gameModeType == gameModeType));
    }

}
