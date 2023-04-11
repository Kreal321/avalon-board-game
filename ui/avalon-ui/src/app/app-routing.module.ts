import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PageHomeComponent } from './features/page-home/page-home.component';
import { PageSignupComponent } from './features/page-signup/page-signup.component';
import { PageLoginComponent } from './features/page-login/page-login.component';
import { PageGameDashboardComponent } from './features/page-game-dashboard/page-game-dashboard.component';
import { PageGameRoomComponent } from './features/page-game-room/page-game-room.component';

const routes: Routes = [
  { path: '', component: PageHomeComponent },
  { path: 'login', component: PageLoginComponent },
  { path: 'signup', component: PageSignupComponent },
  { path: 'home', component: PageHomeComponent },
  { path: 'game', component: PageGameDashboardComponent },
  { path: 'game/:id', component: PageGameRoomComponent},
  { path: '**', component: PageHomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
