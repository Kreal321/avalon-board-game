import { Component, Input, OnChanges } from '@angular/core';

import { Game } from 'src/app/core/models/game.model';

@Component({
  selector: 'app-game-players',
  templateUrl: './game-players.component.html',
  styleUrls: ['./game-players.component.css']
})
export class GamePlayersComponent implements OnChanges {

  @Input() game: Game | undefined;

  constructor() { }

  ngOnChanges(): void {
    this.game?.players.sort((a, b) => a.seatNum - b.seatNum);
  }


}
