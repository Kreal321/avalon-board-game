import { Component, Input, OnChanges } from '@angular/core';
import { CharacterType } from 'src/app/core/enums/characterType.enum';
import { GameStatus } from 'src/app/core/enums/gameStatus.enum';

import { Game } from 'src/app/core/models/game.model';
import { Player } from 'src/app/core/models/player.model';

@Component({
  selector: 'app-game-players',
  templateUrl: './game-players.component.html',
  styleUrls: ['./game-players.component.css']
})
export class GamePlayersComponent implements OnChanges {

  @Input() game: Game | undefined;

  constructor() { }

  ngOnChanges(): void {
    if (this.game) {
      this.game.players.sort((a, b) => a.seatNum - b.seatNum);
      for (let i = 0; i < this.game.players.length; i++) {
        this.game.players[i].seatNum = i + 1;
      }
    }
  }

  isThumbsUp(player: Player): boolean | undefined {
    return this.game?.character?.thumbsUpPlayers?.some(p => p.seatNum == player.seatNum);
  }

  isStarted(): boolean {
    return this.game?.gameStatus != GameStatus.NOT_STARTED;
  }

  getBadgeColor(player: Player): string {
    switch (player.characterType) {
      case CharacterType.GOOD:
        return "success";
      case CharacterType.MERLIN:
      case CharacterType.PERCIVAL:
        return "primary";

      case CharacterType.MORDRED:
      case CharacterType.MORGANA:
      case CharacterType.ASSASSIN:
      case CharacterType.OBERON:
      case CharacterType.EVIL:
        return "danger";
      default:
        return "secondary";
    }

  }


}
