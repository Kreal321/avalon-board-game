import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-form-register',
  templateUrl: './form-register.component.html',
  styleUrls: ['./form-register.component.css']
})
export class FormRegisterComponent {

  token: string = '';
  username: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  register(): void {

    let registerTime = sessionStorage.getItem('registerTime');

    if (registerTime != null) {
      let timeDiff = new Date().getTime() - parseInt(registerTime);
      if (timeDiff < 60000) {
        Swal.fire({
          title: 'Error',
          text: 'You can only retrieve account information once every 60 seconds.',
          icon: 'error',
          confirmButtonText: 'Continue',
        });
        return;
      }
    }

    sessionStorage.setItem('retrieveTime', new Date().getTime().toString());

    Swal.fire({
      title: 'Registering...',
      text: 'Please wait while we register your account',
      icon: 'info',
      confirmButtonText: 'Continue',
    });


    this.userService.register(this.username, this.email, this.preferredName).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Registration Successful',
            text: 'Welcome ' + response.data.preferredName,
            icon: 'success',
            confirmButtonText: 'Continue',
          }).then(() => {
            this.router.navigate(['/game']);
          })
        }
      }
    );
  }

}
