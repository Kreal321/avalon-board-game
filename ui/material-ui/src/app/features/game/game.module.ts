import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'; 

import { GameRoutingModule } from './game-routing.module';

import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';


import { PageGameHomeComponent } from './pages/page-game-home/page-game-home.component';
import { GameJoinComponent } from './components/game-join/game-join.component';
import { GameCreateComponent } from './components/game-create/game-create.component';



@NgModule({
  declarations: [
    PageGameHomeComponent,
    GameJoinComponent,
    GameCreateComponent,
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
