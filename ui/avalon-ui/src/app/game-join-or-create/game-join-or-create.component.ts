import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2'


@Component({
  selector: 'app-game-join-or-create',
  templateUrl: './game-join-or-create.component.html',
  styleUrls: ['./game-join-or-create.component.css']
})
export class GameJoinOrCreateComponent implements OnInit {

  gameNum: number;
  showCreationForm: boolean = false;
  gameModeNum: number = 0;

  constructor() { }

  ngOnInit() {
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
    
      Swal.fire({
        title: 'Success',
        text: 'You have joined game ' + this.gameNum,
        icon: 'success',
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
