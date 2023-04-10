import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';
import { Location, PopStateEvent } from '@angular/common';
import { UserService } from '../../services/user.service';


@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

    constructor(
        private userService: UserService
    ) { }

    logout(): void {
        this.userService.logout();
    }

    isLoggedIn(): boolean {
        return this.userService.isLoggedIn();
    }

}
