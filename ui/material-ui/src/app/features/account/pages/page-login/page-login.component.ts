import { Component } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-page-login',
  templateUrl: './page-login.component.html',
  styleUrls: ['./page-login.component.css']
})
export class PageLoginComponent {

  token: string = '';
  username: string = '';
  userHash: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';
  showPasswordInput: boolean = false;

  constructor(
    private userService: UserService,
    private router: Router,
  ) { 
    this.showPasswordInput = this.router.url != "/login"

    this.router.events.subscribe((val) => {
      this.showPasswordInput = this.router.url != "/login"
    });
  }

  loginWithCredentials(): void {
    this.userService.loginWithCredentials(this.username, this.userHash, this.code).subscribe(
      response => {
        console.log(response);
        if (response.success) {
          Swal.fire({
            title: 'Login Successful',
            text: 'Welcome back ' + response.data.preferredName,
            icon: 'success',
            confirmButtonText: 'Continue',
          }).then(() => {
            this.router.navigate(['/profile']);
          })
        } 
      }
    );
  }

}
