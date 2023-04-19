import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-page-game-home',
  templateUrl: './page-game-home.component.html',
  styleUrls: ['./page-game-home.component.css']
})
export class PageGameHomeComponent {

  gameId: number;

  constructor(
    private route: ActivatedRoute,
  ) { 
    this.gameId = Number(this.route.snapshot.paramMap.get('id'));
  }

}
