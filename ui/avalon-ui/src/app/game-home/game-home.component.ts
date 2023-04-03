import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game-home',
  templateUrl: './game-home.component.html',
  styleUrls: ['./game-home.component.css']
})
export class GameHomeComponent implements OnInit {

  gameId: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.gameId = this.route.snapshot.params['id'];
  }



}
