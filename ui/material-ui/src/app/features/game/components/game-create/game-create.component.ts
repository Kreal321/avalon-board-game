import { Component, OnInit, EventEmitter, Input, Output } from '@angular/core';

import { Game } from 'src/app/core/models/game.model';
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

  gameNum: number = 0;
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


}
