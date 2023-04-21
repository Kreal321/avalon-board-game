import { Component, Input, OnChanges } from '@angular/core';

import { Game } from 'src/app/core/models/game.model';
import { Quest } from 'src/app/core/models/quest.model';

import { GameModeService } from 'src/app/core/services/gameMode.service';

@Component({
  selector: 'app-game-rounds',
  templateUrl: './game-rounds.component.html',
  styleUrls: ['./game-rounds.component.css']
})
export class GameRoundsComponent implements OnChanges{
  
  @Input() game: Game | undefined;
  quests: Quest[] = [];
  currentQuestRound: number = 1;

  constructor(
    public gameModeService: GameModeService
  ) { }

  ngOnChanges(): void {
    if (this.game) {
      this.gameModeService.getGameModeByGameModeType(this.game.gameMode).subscribe((gameMode) => {
        gameMode?.quests.forEach((size, idx) => {
          this.quests.push({questNum: idx + 1, teamSize: size, color: "secondary"});
        });
      });
      this.game.rounds.forEach((round) => {
        switch (round.roundStatus) {
          case "FINAL_TEAM_VOTING_FAIL":
            break;
          case "QUEST_SUCCESS":
            this.quests[round.questNum - 1].color = "success";
            break;
          case "QUEST_FAIL":
            this.quests[round.questNum - 1].color = "danger";
            break;
          default:
            this.quests[round.questNum - 1].color = "primary";
            this.currentQuestRound = round.roundNum;
            break;
        }
      });  
    }
  }
  

  gameSizeLargerThan(size: number): boolean {
    return this.game ? this.game.players.length > size : false;
  }


}
