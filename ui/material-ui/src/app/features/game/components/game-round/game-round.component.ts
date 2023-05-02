import { Component, Input, OnChanges } from '@angular/core';
import { Character } from 'src/app/core/models/character.model';
import { Player } from 'src/app/core/models/player.model';
import { Round } from 'src/app/core/models/round.model';

import { RoundStatus } from 'src/app/core/enums/roundStatus.enum';
import { TeamType } from 'src/app/core/enums/teamType.enum';
import { Game } from 'src/app/core/models/game.model';
import { GameStatus } from 'src/app/core/enums/gameStatus.enum';

@Component({
  selector: 'app-game-round',
  templateUrl: './game-round.component.html',
  styleUrls: ['./game-round.component.css']
})
export class GameRoundComponent implements OnChanges{

  @Input() round: Round | undefined;
  @Input() players: Player[] | undefined;
  @Input() collapsed: boolean = true;

  @Input() game: Game | undefined;

  teamType: TeamType = TeamType.INITIAL;

  constructor() { }

  ngOnChanges(): void {
    if (this.round?.roundStatus == RoundStatus.DISCUSSING) {
      this.teamType = TeamType.FINAL;
    }
  }

  showFinalTeamVote(): boolean {
    return !(this.round?.roundStatus == RoundStatus.INITIAL_TEAM || this.round?.roundStatus == RoundStatus.DISCUSSING);
  }

  showMissionResult(): boolean {
    return this.round?.roundStatus == RoundStatus.FINAL_TEAM_VOTING_SUCCESS || this.round?.roundStatus == RoundStatus.QUEST_SUCCESS || this.round?.roundStatus == RoundStatus.QUEST_FAIL;
  }

  showTeamAssignment(): boolean {
    if (this.game?.gameStatus != GameStatus.IN_PROGRESS) {
      return false;
    }
    if (this.round?.leader.playerId != this.game?.character?.current.playerId) {
      return false;
    }
    if (this.round?.roundStatus == RoundStatus.INITIAL_TEAM || this.round?.roundStatus == RoundStatus.DISCUSSING) {
      return true;
    } 
    return false;
  }

}
