import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-page-game-room',
  templateUrl: './page-game-room.component.html',
  styleUrls: ['./page-game-room.component.css']
})
export class PageGameRoomComponent implements OnInit {

  gameId: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.gameId = this.route.snapshot.params['id'];
  }



}
