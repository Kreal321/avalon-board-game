import { Component } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';

import Swal from 'sweetalert2'


@Component({
  selector: 'app-page-forgot-account',
  templateUrl: './page-forgot-account.component.html',
  styleUrls: ['./page-forgot-account.component.css']
})
export class PageForgotAccountComponent {

  email: string = '';

  constructor(
    private userService: UserService,
  ) { }

  findAccount(): void {
    if (this.email == '') {
      Swal.fire({
        title: 'Error',
        text: 'Please enter an email address',
        icon: 'error',
        confirmButtonText: 'Continue',
      });
      return;
    }

    let retrieveTime = sessionStorage.getItem('retrieveTime');

    if (retrieveTime != null) {
      let timeDiff = new Date().getTime() - parseInt(retrieveTime);
      if (timeDiff < 300000) {
        Swal.fire({
          title: 'Error',
          text: 'You can only retrieve account information once every 5 minutes.',
          icon: 'error',
          confirmButtonText: 'Continue',
        });
        return;
      }
    }

    sessionStorage.setItem('retrieveTime', new Date().getTime().toString());

    Swal.fire({
      title: 'Success',
      text: 'An email has been sent to ' + this.email + ' with your account information.',
      icon: 'success',
      confirmButtonText: 'Continue',
    });

    this.userService.findAccount(this.email).subscribe(res => {
      if (!res.success) {
        Swal.fire({
          title: 'Error',
          text: res.message,
          icon: 'error',
          confirmButtonText: 'Continue',
        });
        }
    });
  }
}
