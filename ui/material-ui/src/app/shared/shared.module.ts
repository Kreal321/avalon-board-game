import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { GameStatusPipe } from './pipes/gameStatus.pipe';


import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';
import { GameRecordsComponent } from './components/game-records/game-records.component';


import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';
import { EnumPrintPipe } from './pipes/enumPrint.pipe';


@NgModule({
  declarations: [
    GameStatusPipe,
    EnumPrintPipe,
    NavComponent,
    FooterComponent,
    GameRecordsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MdbCollapseModule,
    MdbDropdownModule,
  ],
  exports: [
    GameStatusPipe,
    EnumPrintPipe,
    NavComponent,
    FooterComponent,
    GameRecordsComponent
  ]
})
export class SharedModule { }
