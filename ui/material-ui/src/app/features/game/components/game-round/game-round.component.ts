import { Component, Input } from '@angular/core';
import { Character } from 'src/app/core/models/character.model';
import { Player } from 'src/app/core/models/player.model';
import { Round } from 'src/app/core/models/round.model';

@Component({
  selector: 'app-game-round',
  templateUrl: './game-round.component.html',
  styleUrls: ['./game-round.component.css']
})
export class GameRoundComponent {

  @Input() round: Round | undefined;
  @Input() players: Player[] | undefined;
  @Input() collapsed: boolean = true;
  @Input() character: Character | undefined;

}
