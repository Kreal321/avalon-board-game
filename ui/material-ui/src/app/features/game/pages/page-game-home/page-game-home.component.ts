import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Game } from 'src/app/core/models/game.model';

import { GameService } from 'src/app/core/services/game.service';
import { StompService } from 'src/app/core/services/stomp.service';


@Component({
  selector: 'app-page-game-home',
  templateUrl: './page-game-home.component.html',
  styleUrls: ['./page-game-home.component.css']
})
export class PageGameHomeComponent {

  gameId: number;
  game: Game | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService,
    private stompService: StompService
  ) { 
    this.gameId = Number(this.route.snapshot.paramMap.get('id'));
    this.gameService.findGameByGameId(this.gameId).subscribe(
      response => {
        if (response.success) {
          this.game = response.data;
          console.log(response.data);
          this.stompService.subscribe(this.game!.gameId, (message : string) => {
            console.log("Updated");
            this.updateGame();
          });
        }
      }
    )
  }

  private updateGame(): void {
    this.gameService.findGameByGameId(this.gameId).subscribe(
      response => {
        if (response.success) {
          this.game = response.data;
        }
      }
    )
  }

}
