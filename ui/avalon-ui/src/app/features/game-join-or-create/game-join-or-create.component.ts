import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2'
import { GameService } from '../../core/services/game.service';
import { GameModeService } from '../../core/services/gameMode.service';
import { GameMode } from '../../core/models/gameMode.model';
import { Game } from '../../core/models/game.model';

@Component({
  selector: 'app-game-join-or-create',
  templateUrl: './game-join-or-create.component.html',
  styleUrls: ['./game-join-or-create.component.css']
})
export class GameJoinOrCreateComponent implements OnInit {

  showCreationForm: boolean = false;

  gameNum: number;
  gameModeNum: number = 0;
  
  gameModePlayers: number[];
  gameModes: GameMode[];

  constructor(
    private gameService: GameService,
    private gameModeService: GameModeService
  ) { 
    this.gameModePlayers = [5, 6, 7, 8, 9, 10];
  }

  ngOnInit() {
    this.gameModeService.getGameModes().subscribe(
      data => {
        this.gameModes = data;
      }
    )
  }

  toggleCreationForm(): void {
    this.showCreationForm = !this.showCreationForm;

  }

  joinGame(): void {
    if (this.gameNum == null || this.gameNum.toString().length != 4) {
      Swal.fire({
        title: 'Error',
        text: 'Please enter a 4 digit game number',
        icon: 'error',
      })
      return;
    }
    

    this.gameService.joinGameByGameNum(this.gameNum).subscribe(
      data => {
        if (data) {
          let current = this.gameModes.find(x => x.gameModeType == data.gameMode);

          Swal.fire({
            title: 'Success',
            html:`
              <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text">Game Mode: </span>
                  </div>
                  <select class="form-control bg-white" disabled>
                      <option selected>${current.name}</option>
                  </select>
              </div>
            `,
            icon: 'success',
            confirmButtonText: 'Join Game'
          })
        }
      })
    
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
    if (this.gameNum == null || this.gameNum.toString().length != 4) {
      Swal.fire({
        title: 'Error',
        text: 'Please enter a 4 digit game number',
        icon: 'error',
      })
      return;
    }

    Swal.fire({
      title: 'Success',
      text: 'You have created a game with game number ' + this.gameNum + ' and game mode ' + this.gameModeNum + '.',
      icon: 'success',
    })
  }




}
