import { Component, OnInit } from '@angular/core';


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


}
