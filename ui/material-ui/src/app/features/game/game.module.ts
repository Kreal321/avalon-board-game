import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'; 

import { GameRoutingModule } from './game-routing.module';

import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';

import { PageHomeComponent } from './pages/page-home/page-home.component';
import { PageGameHomeComponent } from './pages/page-game-home/page-game-home.component';
import { GameJoinComponent } from './components/game-join/game-join.component';
import { GameCreateComponent } from './components/game-create/game-create.component';
import { GameHistoryComponent } from './components/game-history/game-history.component';
import { GameInfoComponent } from './components/game-info/game-info.component';
import { GamePlayersComponent } from './components/game-players/game-players.component';



@NgModule({
  declarations: [
    PageGameHomeComponent,
    GameJoinComponent,
    GameCreateComponent,
    PageHomeComponent,
    GameHistoryComponent,
    GameInfoComponent,
    GamePlayersComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    CommonModule,
    GameRoutingModule,
    MdbTabsModule
  ],
  exports: []
})
export class GameModule { }
