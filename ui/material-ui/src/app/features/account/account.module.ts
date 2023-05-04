import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common'; 

// MDB Modules
import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';
import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { MdbTooltipModule } from 'mdb-angular-ui-kit/tooltip';

import { AccountRoutingModule } from './account-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { PageLoginComponent } from './pages/page-login/page-login.component';
import { PageRegisterComponent } from './pages/page-register/page-register.component';
import { PageProfileComponent } from './pages/page-profile/page-profile.component';
import { UserInfoComponent } from './components/user-info/user-info.component';
import { PageForgotAccountComponent } from './pages/page-forgot-account/page-forgot-account.component';


@NgModule({
  declarations: [
    PageLoginComponent,
    PageRegisterComponent,
    PageProfileComponent,
    UserInfoComponent,
    PageForgotAccountComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    CommonModule,
    AccountRoutingModule,
    MdbTabsModule,
    MdbAccordionModule,
    MdbTooltipModule,
    SharedModule
  ],
  exports: []
})
export class AccountModule { }
