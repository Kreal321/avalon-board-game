import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'; 

import { GameRoutingModule } from './game-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';

import { PageHomeComponent } from './pages/page-home/page-home.component';
import { PageGameHomeComponent } from './pages/page-game-home/page-game-home.component';
import { GameJoinComponent } from './components/game-join/game-join.component';
import { GameCreateComponent } from './components/game-create/game-create.component';
import { GameHistoryComponent } from './components/game-history/game-history.component';
import { GameInfoComponent } from './components/game-info/game-info.component';
import { GamePlayersComponent } from './components/game-players/game-players.component';
import { GameRoundComponent } from './components/game-round/game-round.component';
import { GameRoundsComponent } from './components/game-rounds/game-rounds.component';
import { GameQuestDisplayComponent } from './components/game-quest-display/game-quest-display.component';
import { PlayerBadgeComponent } from './components/player-badge/player-badge.component';
import { VoteContainerComponent } from './components/vote-container/vote-container.component';
import { TeamContainerComponent } from './components/team-container/team-container.component';
import { TeamAssignmentComponent } from './components/team-assignment/team-assignment.component';



@NgModule({
  declarations: [
    PageGameHomeComponent,
    GameJoinComponent,
    GameCreateComponent,
    PageHomeComponent,
    GameHistoryComponent,
    GameInfoComponent,
    GamePlayersComponent,
    GameRoundComponent,
    GameRoundsComponent,
    GameQuestDisplayComponent,
    PlayerBadgeComponent,
    VoteContainerComponent,
    TeamContainerComponent,
    TeamAssignmentComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    CommonModule,
    GameRoutingModule,
    MdbTabsModule,
    MdbAccordionModule,
    SharedModule
  ],
  exports: []
})
export class GameModule { }
