import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { Player } from 'src/app/core/models/player.model';

@Component({
  selector: 'app-player-container',
  templateUrl: './player-container.component.html',
  styleUrls: ['./player-container.component.css']
})
export class PlayerContainerComponent implements OnInit {

  @Input() player: Player;
  @Input() color: string;

  ngOnInit() {}



}
