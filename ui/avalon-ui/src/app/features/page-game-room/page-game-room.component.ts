import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Player } from 'src/app/core/models/player.model';
import { CharacterType } from 'src/app/core/enums/characterType.enum';


@Component({
  selector: 'app-page-game-room',
  templateUrl: './page-game-room.component.html',
  styleUrls: ['./page-game-room.component.css']
})
export class PageGameRoomComponent implements OnInit {

  gameId: number;
  player1: Player = { playerId: 1, displayName: "Player 11", characterType: CharacterType.MERLIN, seatNum: 1 };
  color: string = "default";

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.gameId = this.route.snapshot.params['id'];
  }



}
