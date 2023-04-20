import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';

import Swal from 'sweetalert2'

import { GameService } from 'src/app/core/services/game.service';

@Component({
  selector: 'app-game-join',
  templateUrl: './game-join.component.html',
  styleUrls: ['./game-join.component.css']
})
export class GameJoinComponent {

  @Input() showCreationForm: boolean = false;
  @Output() showCreationFormChange = new EventEmitter<boolean>();

  gameNum: number | undefined;

  constructor(
    private gameService: GameService,
    private router: Router
  ) { }

  toggleCreationForm() : void {
    this.showCreationForm = !this.showCreationForm;
    this.showCreationFormChange.emit(this.showCreationForm);
  }

  joinGame(): void {
    if (this.gameNum == undefined || this.gameNum < 0 || this.gameNum.toString().length != 4) {
      Swal.fire({
        title: 'Error',
        text: 'Please enter a 4 digit game number',
        icon: 'error',
      })
      return;
    }

    this.gameService.joinGameByGameNum(this.gameNum).subscribe(
      response => {
        if (response.success) {
          this.router.navigate(['/game/' + response.data.gameId]);
        }
      }
    )
  }

}
