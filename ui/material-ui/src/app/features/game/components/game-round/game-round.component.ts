import { Component, Input } from '@angular/core';
import { Round } from 'src/app/core/models/round.model';

@Component({
  selector: 'app-game-round',
  templateUrl: './game-round.component.html',
  styleUrls: ['./game-round.component.css']
})
export class GameRoundComponent {

  @Input() round: Round | undefined;
  

}
