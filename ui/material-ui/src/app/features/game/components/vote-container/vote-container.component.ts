import { Component, Input, OnChanges } from '@angular/core';
import { RoundStatus } from 'src/app/core/enums/roundStatus.enum';
import { Vote } from 'src/app/core/models/vote.model';
import { Player } from 'src/app/core/models/player.model';
import { Character } from 'src/app/core/models/character.model';

import { GameService } from 'src/app/core/services/game.service';
import { Round } from 'src/app/core/models/round.model';

import Swal from 'sweetalert2'

@Component({
  selector: 'app-vote-container',
  templateUrl: './vote-container.component.html',
  styleUrls: ['./vote-container.component.css']
})
export class VoteContainerComponent implements OnChanges{

  // @Input() status: RoundStatus | undefined;
  // @Input() votes: Vote[] | undefined;

  @Input() round: Round | undefined;
  @Input() players: Player[] | undefined;
  @Input() character: Character | undefined;

  color: string = "secondary";
  showVotes: boolean = false;
  canVote: boolean = true;

  constructor(
    private gameService: GameService
  ) { }

  ngOnChanges(): void {
    if (this.round?.roundStatus === RoundStatus.FINAL_TEAM_VOTING) {
      this.color = "primary";
      this.showVotes = true;
    } else {
      this.color = "secondary";
      this.showVotes = false;
    }
  }

  getBadgeColor(player: Player): string {
    let v : Vote | undefined = this.round?.votes?.find(vote => vote.playerId === player.playerId);
    if (v) {
      if (v?.playerId === this.character?.current.playerId) {
        this.canVote = false;
      }
      if (this.round?.roundStatus === RoundStatus.FINAL_TEAM_VOTING) {
        return "info";
      } else if (v.accept) {
        return "success";
      } else {
        return "danger";
      }
    }
    return "secondary";
  }

  vote(accept: boolean): void {
    this.gameService.vote(this.round!.gameId, this.round!.roundId, accept).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Success',
            text: 'You have voted' + (accept ? ' to accept' : ' to reject') + ' the final team assignment.',
            icon: 'success',
          })
        }
      }
    );
  }

}
