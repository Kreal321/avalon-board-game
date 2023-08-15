import {Component, Input} from '@angular/core';
import {Player} from "../../../../core/models/player.model";
import {CharacterType} from "../../../../core/enums/characterType.enum";

@Component({
  selector: 'app-character-badge',
  templateUrl: './character-badge.component.html',
  styleUrls: ['./character-badge.component.css']
})
export class CharacterBadgeComponent {
  @Input() color: string = "secondary";
  @Input() character: CharacterType | undefined;
}
