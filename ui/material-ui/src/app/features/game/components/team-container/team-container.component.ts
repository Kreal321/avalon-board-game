import { Component, Input } from '@angular/core';
import { Player } from 'src/app/core/models/player.model';
import { Team } from 'src/app/core/models/team.model.';

@Component({
  selector: 'app-team-container',
  templateUrl: './team-container.component.html',
  styleUrls: ['./team-container.component.css']
})
export class TeamContainerComponent {


  @Input() team: Team | undefined;
  @Input() players: Player[] | undefined;


  
  constructor() { }

}
