import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { GameStatusPipe } from './pipes/gameStatus.pipe';
import { PlayerPipe } from './pipes/player.pipe';

import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';


import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';


@NgModule({
  declarations: [
    GameStatusPipe,
    PlayerPipe,
    NavComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MdbCollapseModule,
    MdbDropdownModule,
  ],
  exports: [
    GameStatusPipe,
    PlayerPipe,
    NavComponent,
    FooterComponent
  ]
})
export class SharedModule { }
