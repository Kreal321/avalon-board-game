import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PageGameHomeComponent } from './pages/page-game-home/page-game-home.component';


const routes: Routes = [
    { path: 'game', component: PageGameHomeComponent },
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class GameRoutingModule { }