import { NgModule } from '@angular/core';

import { MatSlideToggleModule } from '@angular/material/slide-toggle';


import { AccountRoutingModule } from './account-routing.module';
import { PageLoginComponent } from './pages/page-login/page-login.component';
import { PageRegisterComponent } from './pages/page-register/page-register.component';


@NgModule({
  declarations: [
    PageLoginComponent,
    PageRegisterComponent
  ],
  imports: [
    AccountRoutingModule,
    MatSlideToggleModule
  ],
  exports: []
})
export class AccountModule { }
