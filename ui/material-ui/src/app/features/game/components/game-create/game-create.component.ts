import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';

import Swal from 'sweetalert2';

import { GameMode } from 'src/app/core/models/gameMode.model';
import { GameService } from 'src/app/core/services/game.service';
import { GameModeService } from 'src/app/core/services/gameMode.service';

@Component({
  selector: 'app-game-create',
  templateUrl: './game-create.component.html',
  styleUrls: ['./game-create.component.css']
})
export class GameCreateComponent implements OnInit {

  @Input() showCreationForm: boolean = false;
  @Output() showCreationFormChange = new EventEmitter<boolean>();

  gameNum: number | undefined;
  gameModeNum: number = 0;

  gameModes: GameMode[] = [];
  gameModeSelected: GameMode | undefined;

  constructor(
    private gameService: GameService,
    private gameModeService: GameModeService,
  ) { }

  ngOnInit() {
    this.gameModeService.getGameModes().subscribe(
      data => {
        this.gameModes = data;
      }
    )
  }

  gameModeIsSelected() : boolean {
    return this.gameModeSelected != undefined;
  }

  gameModeChange() : void {
    this.gameModeService.getGameModeById(this.gameModeNum).subscribe(
      data => {
        this.gameModeSelected = data;
      }
    )
  }

  toggleCreationForm() : void {
    this.showCreationForm = !this.showCreationForm;
    this.showCreationFormChange.emit(this.showCreationForm);
  }

  createGame(): void {
    if (this.gameModeNum == 0) {
      Swal.fire({
        title: 'Error',
        text: 'Please select a game mode',
        icon: 'error',
      })
      return;
    }
    if (this.gameNum == undefined || this.gameNum < 0 || this.gameNum.toString().length != 4) {
      Swal.fire({
        title: 'Error',
        text: 'Please enter a 4 digit game number',
        icon: 'error',
      })
      return;
    }

    this.gameModeService.getGameModeById(this.gameModeNum).subscribe(
      // gameMode => {
      //   this.gameService.createGame(gameMode.numberOfPlayers, this.gameNum, gameMode.gameModeType).subscribe(
      //     response => {
      //       if (response.success) {
      //         Swal.fire({
      //           title: 'Created Game Successfully',
      //           text: 'You have created a game with game number ' + this.gameNum + ' and game mode ' + this.gameModeNum + '.',
      //           icon: 'success',
      //           confirmButtonText: 'Join Game',
      //         }).then(() => {
      //           this.joinGame();
      //         })
      //       }
      //     }
      //   )
      // }
    );
  }


}
