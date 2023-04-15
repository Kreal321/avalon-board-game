import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PathIsActivePipe } from './pipes/pathIsActive.pipe';
import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';


import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';


@NgModule({
  declarations: [
    PathIsActivePipe,
    NavComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    MdbCollapseModule,
    MdbDropdownModule,
  ],
  exports: [
    PathIsActivePipe,
    NavComponent,
    FooterComponent
  ]
})
export class SharedModule { }
