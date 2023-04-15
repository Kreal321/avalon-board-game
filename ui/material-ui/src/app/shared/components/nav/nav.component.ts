import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  getPath(): string {
    return this.router.url;
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  isLoggedIn(): boolean {
    return this.userService.isLoggedIn();
  }
}
