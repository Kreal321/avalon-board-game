import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from './core/services/auth.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './core/components/footer/footer.component';
import { NavbarComponent } from './core/components/navbar/navbar.component';
import { GameIntroComponent } from './features/game-intro/game-intro.component';
import { GameJoinOrCreateComponent } from './features/game-join-or-create/game-join-or-create.component';
import { GameHomeComponent } from './features/game-home/game-home.component';
import { PageHomeComponent } from './features/page-home/page-home.component';
import { GameModeContainerComponent } from './features/components/game-mode-container/game-mode-container.component';
import { LoginOrSignupComponent } from './features/components/login-or-signup/login-or-signup.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    NavbarComponent,
    GameIntroComponent,
    GameJoinOrCreateComponent,
    GameHomeComponent,
    PageHomeComponent,
    GameModeContainerComponent,
    LoginOrSignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthService, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
