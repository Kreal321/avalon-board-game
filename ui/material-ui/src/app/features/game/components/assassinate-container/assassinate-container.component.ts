import { Component, Input, OnChanges } from '@angular/core';
import { CharacterType } from 'src/app/core/enums/characterType.enum';
import { GameStatus } from 'src/app/core/enums/gameStatus.enum';
import { Character } from 'src/app/core/models/character.model';
import { Game } from 'src/app/core/models/game.model';
import { Player } from 'src/app/core/models/player.model';
import { GameService } from 'src/app/core/services/game.service';

import Swal from 'sweetalert2'


@Component({
  selector: 'app-assassinate-container',
  templateUrl: './assassinate-container.component.html',
  styleUrls: ['./assassinate-container.component.css']
})
export class AssassinateContainerComponent implements OnChanges {

  @Input() game: Game | undefined;
  showTargetList: boolean = false;
  character: Character | undefined;
  target: Player | undefined;

  constructor(
    private gameService: GameService
  ) { }

  ngOnChanges(): void {
    if (this.game) {
      this.character = this.game.character;
      if (this.game.gameStatus == GameStatus.ASSASSIN_FLOP){
        this.showTargetList = true;
      } else {
        this.showTargetList = false;
      }
    }
  }

  flopCard(): void {
    this.gameService.assassinFlop(this.game!.gameId).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Success!',
            text: 'Flop successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        }
      });
  }

  assassinate(): void {
    if (this.target == undefined) {
      Swal.fire({
        title: 'Assassinate Failed',
        text: 'You must select a target!',
        icon: 'error',
        confirmButtonText: 'OK'
      });
      return;
    }
    this.gameService.assassinAssassinate(this.game!.gameId, this.target!.playerId).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Success!',
            text: 'Assassinate successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        }
      });
  }

  isAssassin(): boolean {
    return this.character? this.character.characterType == CharacterType.ASSASSIN : false;
  }

  showAssassinate(): boolean {
    
    if (this.game == undefined) {
      return false;
    }

    if (this.game!.character.characterType != CharacterType.ASSASSIN) {
      return false;
    }

    if (this.game!.gameStatus == GameStatus.IN_PROGRESS || this.game!.gameStatus == GameStatus.ASSASSIN_FLOP){
      return true;
    }

    return false;
  
  }

  getBadgeColor(player: Player): string {
    if (this.target?.playerId == player.playerId) {
      return "danger";
    } else {
      return "secondary";
    }
  }

  toggleTarget(player: Player): void {
    this.target = player;
  }

}
