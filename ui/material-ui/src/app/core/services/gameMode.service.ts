import { Injectable, OnInit } from '@angular/core';
import { GameMode } from '../models/gameMode.model';
import { GameModeType } from '../enums/gameModeType.enum';
import { Observable, of } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class GameModeService implements OnInit {

    private gameModes : GameMode[] = [
        { id: 51, name: "5 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 5, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 52, name: "5 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 5, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 53, name: "5 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 5, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 61, name: "6 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 6, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 62, name: "6 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 6, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 63, name: "6 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 6, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 71, name: "7 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 7, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 72, name: "7 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 7, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 73, name: "7 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 7, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 81, name: "8 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 8, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 82, name: "8 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 8, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 83, name: "8 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 8, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 91, name: "9 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 9, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 92, name: "9 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 9, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 93, name: "9 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 9, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },
        { id: 101, name: "10 Players - Basic", gameModeType: GameModeType.FIVE_BASIC, numberOfPlayers: 10, tabName: "Basic", description: "The standard setup, seems to be best for beginners.", rules: "Merlin, 2 generic good guys VS Assassin, generic bad guy." },
        { id: 102, name: "10 Players - Morgana", gameModeType: GameModeType.FIVE_MORGANA_PERCIVAL, tabName: "Morgana", numberOfPlayers: 10, description: "The most enjoyable games, without a doubt.", rules: "Merlin, Percival, one generic good guy VS Assassin, Morgana." },
        { id: 103, name: "10 Players - Mordred", gameModeType: GameModeType.FIVE_MORDRED_PERCIVAL, tabName: "Mordred", numberOfPlayers: 10, description: "asily the funnest games. Merlin only knows the Assassin, Percival knows the Merlin, but the bad guys know each other.", rules: "Merlin, Percival, one generic good guy VS Assassin, Mordred." },

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