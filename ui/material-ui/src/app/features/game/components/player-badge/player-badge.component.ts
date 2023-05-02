import { Component, Input } from '@angular/core';

import { Player } from 'src/app/core/models/player.model';

@Component({
  selector: 'app-player-badge',
  templateUrl: './player-badge.component.html',
  styleUrls: ['./player-badge.component.css']
})
export class PlayerBadgeComponent {

  @Input() player: Player | undefined;
  @Input() color: string = "secondary";
  @Input() isThumbsUp: boolean| undefined;
  @Input() showAssassinated: boolean = false;

  constructor() { }

  isAssassinated(): boolean | undefined {
    return this.showAssassinated && this.player?.isAssassinated;
  }

}
