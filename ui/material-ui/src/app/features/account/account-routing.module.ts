import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PageLoginComponent } from './pages/page-login/page-login.component';
import { PageRegisterComponent } from './pages/page-register/page-register.component';
import { PageProfileComponent } from './pages/page-profile/page-profile.component';
import { PageForgotAccountComponent } from './pages/page-forgot-account/page-forgot-account.component';
import { PageUpdateComponent } from './pages/page-update/page-update.component';
import { PageRegisterTempComponent } from './pages/page-register-temp/page-register-temp.component';

const routes: Routes = [
    { path: 'login', component: PageLoginComponent },
    { path: 'register', component: PageRegisterComponent },
    { path: 'register/temp', component: PageRegisterTempComponent },
    { path: 'profile', component: PageProfileComponent },
    { path: 'update', component: PageUpdateComponent },
    { path: 'forgot', component: PageForgotAccountComponent },
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class AccountRoutingModule { }