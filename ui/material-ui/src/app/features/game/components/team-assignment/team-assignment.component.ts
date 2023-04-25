import { Component, Input, OnChanges } from '@angular/core';
import { TeamType } from 'src/app/core/enums/teamType.enum';
import { Player } from 'src/app/core/models/player.model';
import { Round } from 'src/app/core/models/round.model';

import { GameService } from 'src/app/core/services/game.service';

import Swal from 'sweetalert2'

@Component({
  selector: 'app-team-assignment',
  templateUrl: './team-assignment.component.html',
  styleUrls: ['./team-assignment.component.css']
})
export class TeamAssignmentComponent implements OnChanges{

  @Input() round: Round | undefined;
  @Input() players: Player[] | undefined;
  @Input() teamType: TeamType = TeamType.INITIAL;

  selectedTeamMembers: number[] = [];

  constructor(
    private gameService: GameService
  ) { }

  ngOnChanges(): void {
  }

  getBadgeColor(player: Player): string {
    if (this.selectedTeamMembers.includes(player.playerId)) {
      return "success";
    } else {
      return "secondary";
    }
  }

  toggleTeamMember(player: Player): void {
    if (this.selectedTeamMembers.includes(player.playerId)) {
      this.selectedTeamMembers = this.selectedTeamMembers.filter(num => num !== player.playerId);
    } else {
      this.selectedTeamMembers.push(player.playerId);
    }
  }

  canSubmit(): boolean {
    return this.selectedTeamMembers.length === this.round?.teamSize;
  }

  submitTeam(): void {
    this.gameService.createTeam(this.round!.gameId, this.round!.roundId, this.selectedTeamMembers, this.teamType).subscribe(
      data => {
        if (data.success) {
          Swal.fire({
            title: 'Success!',
            text: 'Team submitted successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        }
      }
    );
  }

}
