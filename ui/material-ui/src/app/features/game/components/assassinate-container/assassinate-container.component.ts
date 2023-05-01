import { Component, Input } from '@angular/core';
import { CharacterType } from 'src/app/core/enums/characterType.enum';
import { Character } from 'src/app/core/models/character.model';
import { GameService } from 'src/app/core/services/game.service';

@Component({
  selector: 'app-assassinate-container',
  templateUrl: './assassinate-container.component.html',
  styleUrls: ['./assassinate-container.component.css']
})
export class AssassinateContainerComponent {

  @Input() character: Character | undefined;

  constructor(
    private gameService: GameService
  ) { }

  assassinate(): void {
  }

  isAssassin(): boolean {
    return this.character? this.character.characterType == CharacterType.ASSASSIN : false;
  }

}
