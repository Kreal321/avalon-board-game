import { Component, Input, OnChanges } from '@angular/core';
import { CharacterType } from 'src/app/core/enums/characterType.enum';
import { RoundStatus } from 'src/app/core/enums/roundStatus.enum';
import { TeamType } from 'src/app/core/enums/teamType.enum';
import { Character } from 'src/app/core/models/character.model';
import { Round } from 'src/app/core/models/round.model';
import { TeamMember } from 'src/app/core/models/teamMember';
import { GameService } from 'src/app/core/services/game.service';

import Swal from 'sweetalert2'

@Component({
  selector: 'app-mission-container',
  templateUrl: './mission-container.component.html',
  styleUrls: ['./mission-container.component.css']
})
export class MissionContainerComponent implements OnChanges{

  @Input() round: Round | undefined;
  @Input() character: Character | undefined;

  teamMembers: TeamMember[] | undefined;
  color: string = "secondary";
  showMissioning: boolean = false;
  canVote: boolean = false;
  title: string = "";

  constructor(
    private gameService: GameService
  ) { }

  ngOnChanges(): void {
    switch (this.round?.roundStatus) {
      case RoundStatus.FINAL_TEAM_VOTING_SUCCESS:
        this.color = "primary";
        this.title = "In Progress";
        break;
      case RoundStatus.QUEST_SUCCESS:
        this.color = "success";
        this.title = "Success with " + this.round?.numFails + " fails";
        break;
      case RoundStatus.QUEST_FAIL:
        this.color = "danger";
        this.title = "Fail with " + this.round?.numFails + " fails";
        break;
      default:
        this.color = "secondary";
        this.title = "";
        break;
    }
    this.teamMembers = this.round?.teams?.find(team => team.teamType == TeamType.FINAL)?.teamMembers;
    if (this.round?.roundStatus === RoundStatus.FINAL_TEAM_VOTING_SUCCESS && this.teamMembers?.map(tm => tm.player.playerId).includes(this.character!.current.playerId)) {
      this.showMissioning = true;
    }
  }

  getBadgeColor(teamMember: TeamMember): string {
    
    switch (teamMember.status) {
      case "MISSION_SUCCESS":
        return "success";
      case "MISSION_FAILED":
        return "danger";
      case "MISSION_PENDING":
        if (teamMember.player.playerId === this.character?.current.playerId) {
          this.canVote = true;
        }
        return "secondary";
      default:
        return "info";
    }
  }

  isGood(): boolean {

    let type : CharacterType | undefined = this.character?.current.characterType;
    return type == CharacterType.MERLIN || type == CharacterType.PERCIVAL || type == CharacterType.GOOD;

  }

  missioning(success: boolean): void {
    if (this.round?.roundStatus === RoundStatus.FINAL_TEAM_VOTING_SUCCESS) {
      this.gameService.mission(this.round!.gameId, this.round!.roundId, success).subscribe(
        response => {
          if (response.success) {
            Swal.fire({
              title: 'Missioning',
              text: 'Your mission' + (success ? ' succeeded' : ' failed') + '!',
              icon: 'success',
              confirmButtonText: 'Ok'
            })
          }
        }
      );
    }
  }



}
