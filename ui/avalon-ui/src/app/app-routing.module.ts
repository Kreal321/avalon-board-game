import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameHomeComponent } from './features/game-home/game-home.component';
import { GameJoinOrCreateComponent } from './features/game-join-or-create/game-join-or-create.component';

import { PageHomeComponent } from './features/page-home/page-home.component';
import { PageSignupComponent } from './features/page-signup/page-signup.component';
import { PageLoginComponent } from './features/page-login/page-login.component';

const routes: Routes = [
  { path: '', component: PageHomeComponent },
  { path: 'login', component: PageLoginComponent },
  { path: 'signup', component: PageSignupComponent },
  { path: 'home', component: PageHomeComponent },
  { path: 'game', component: GameJoinOrCreateComponent },
  { path: 'game/:id', component: GameHomeComponent},
  { path: '**', component: PageHomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
