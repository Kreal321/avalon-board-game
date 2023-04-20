import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import Swal from 'sweetalert2'

import { GameMode } from 'src/app/core/models/gameMode.model';
import { Game } from 'src/app/core/models/game.model';

import { GameService } from 'src/app/core/services/game.service';


@Component({
  selector: 'app-page-game-home',
  templateUrl: './page-game-home.component.html',
  styleUrls: ['./page-game-home.component.css']
})
export class PageGameHomeComponent implements OnInit {

  gameId: number;
  game: Game | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private gameService: GameService,
  ) { 
    this.gameId = Number(this.route.snapshot.paramMap.get('id'));
    this.gameService.findGameByGameId(this.gameId).subscribe(
      response => {
        if (response.success) {
          this.game = response.data;
          console.log(response.data);
        }
      }
    )
  }

  ngOnInit(): void {

  }



}
