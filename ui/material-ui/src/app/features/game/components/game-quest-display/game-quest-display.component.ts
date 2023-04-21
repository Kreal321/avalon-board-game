import { Component, Input } from '@angular/core';

import { Quest } from 'src/app/core/models/quest.model';


@Component({
  selector: 'app-game-quest-display',
  templateUrl: './game-quest-display.component.html',
  styleUrls: ['./game-quest-display.component.css']
})
export class GameQuestDisplayComponent {

  @Input() quest: Quest | undefined;

}
