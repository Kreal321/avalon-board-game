import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PageHomeComponent } from './pages/page-home/page-home.component';
import { PageGameHomeComponent } from './pages/page-game-home/page-game-home.component';


const routes: Routes = [
    { path: '', component: PageHomeComponent},
    { path: 'game', component: PageHomeComponent },
    { path: 'game/:id', component: PageGameHomeComponent },
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class GameRoutingModule { }