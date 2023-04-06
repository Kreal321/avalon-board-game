import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GameMode } from '../../../core/models/gameMode.model';
import { GameModeType } from '../../../core/enums/gameModeType.enum';
import { GameModeService } from '../../../core/services/gameMode.service';


@Component({
  selector: 'app-game-mode-container',
  templateUrl: './game-mode-container.component.html',
  styleUrls: ['./game-mode-container.component.css']
})
export class GameModeContainerComponent implements OnInit {

  @Input() gameModeId: number;
  @Input() numberOfPlayers: number;
  @Output() gameModeIdChange = new EventEmitter<Number>();

  gameModes: GameMode[];

  constructor(private gameModeService: GameModeService) { }

  ngOnInit() {
    this.gameModeService.getGameModesByNumberOfPlayers(this.numberOfPlayers).subscribe(
      data => {
        this.gameModes = data;
      }
    )
  }

  onClicked(gameModeId: number) {
    this.gameModeIdChange.emit(this.gameModeId);
  }


}
