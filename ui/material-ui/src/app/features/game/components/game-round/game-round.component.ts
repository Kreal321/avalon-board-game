import { Component, Input, OnChanges } from '@angular/core';
import { Character } from 'src/app/core/models/character.model';
import { Player } from 'src/app/core/models/player.model';
import { Round } from 'src/app/core/models/round.model';

import { RoundStatus } from 'src/app/core/enums/roundStatus.enum';
import { TeamType } from 'src/app/core/enums/teamType.enum';

@Component({
  selector: 'app-game-round',
  templateUrl: './game-round.component.html',
  styleUrls: ['./game-round.component.css']
})
export class GameRoundComponent implements OnChanges{

  @Input() round: Round | undefined;
  @Input() players: Player[] | undefined;
  @Input() collapsed: boolean = true;

  @Input() character: Character | undefined;

  showTeamAssignment: boolean = false;
  teamType: TeamType = TeamType.INITIAL;


  constructor() { }

  ngOnChanges(): void {
    if (this.round?.roundStatus == RoundStatus.INITIAL_TEAM) {
      this.showTeamAssignment = true;
      this.teamType = TeamType.INITIAL;
    } else if (this.round?.roundStatus == RoundStatus.DISCUSSING) {
      this.showTeamAssignment = true;
      this.teamType = TeamType.FINAL;
    } else {
      this.showTeamAssignment = false;
    }
  }

}
