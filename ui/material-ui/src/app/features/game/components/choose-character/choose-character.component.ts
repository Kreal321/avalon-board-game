import {Component, Input} from '@angular/core';
import {Game} from "../../../../core/models/game.model";
import {GameStatus} from "../../../../core/enums/gameStatus.enum";
import {CharacterType} from "../../../../core/enums/characterType.enum";
import {Player} from "../../../../core/models/player.model";
import {GameService} from "../../../../core/services/game.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-choose-character',
  templateUrl: './choose-character.component.html',
  styleUrls: ['./choose-character.component.css']
})
export class ChooseCharacterComponent {

  @Input() game: Game | undefined;
  characters: CharacterType[] = [CharacterType.ASSASSIN, CharacterType.MERLIN, CharacterType.MORDRED, CharacterType.MORGANA, CharacterType.OBERON, CharacterType.PERCIVAL, CharacterType.GOOD, CharacterType.EVIL];
  selectedCharacter: CharacterType | undefined;

  constructor(
    private gameService: GameService
  ) { }

  toggleCharacter(c: CharacterType) {
    this.selectedCharacter = c;
  }

  getBadgeColor(c: CharacterType): string {
    if (this.game!.character.current.characterType == c) {
      return "primary";
    }
    if (this.selectedCharacter == c) {
      return "success";
    }

    return "secondary";
  }

  canSubmit(): boolean {
    return this.selectedCharacter != undefined && this.selectedCharacter != this.game!.character.current.characterType;
  }

  submitCharacter() {
    this.gameService.chooseCharacter(this.game!.gameId, this.game!.character.current.playerId, this.selectedCharacter!).subscribe(
      data => {
        if (data.success) {
          Swal.fire({
            title: 'Success!',
            text: 'Choose character successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        }
      });
  }

}
