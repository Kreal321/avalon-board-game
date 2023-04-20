import { Component, Input, OnChanges } from '@angular/core';

import { Game } from 'src/app/core/models/game.model';
import { GameMode } from 'src/app/core/models/gameMode.model';

import { GameService } from 'src/app/core/services/game.service';
import { GameModeService } from 'src/app/core/services/gameMode.service';



@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.css']
})
export class GameInfoComponent implements OnChanges {

  @Input() game: Game | undefined;

  gameMode: GameMode | undefined;

  constructor(
    private gameService: GameService,
    private gameModeService: GameModeService,
  ) { }

  ngOnChanges(): void {
    if (this.game) {
      this.gameModeService.getGameModeByGameModeType(this.game.gameMode).subscribe(
        data => {
          this.gameMode = data;
        }
      )
    }
  }

  startGame(): void {
    if (this.game) {
      this.gameService.startGame(this.game.gameId).subscribe(
        data => {
          console.log(data);
        }
      )
    }
  }

}
