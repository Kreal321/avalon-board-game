import { Component } from '@angular/core';

import { UserService } from 'src/app/core/services/user.service';
import Swal from 'sweetalert2'
import { Router } from '@angular/router';


@Component({
  selector: 'app-page-register-temp',
  templateUrl: './page-register-temp.component.html',
  styleUrls: ['./page-register-temp.component.css']
})
export class PageRegisterTempComponent {

  constructor(
    private UserService: UserService,
    private router: Router
  ) {
    if (UserService.isLoggedIn()) {
      this.router.navigate(['/profile']);
    } else {
      Swal.fire({
        title: 'Would you like to create a guest account?',
        showDenyButton: true,
        allowOutsideClick: false,
        icon: 'warning',
        confirmButtonText: 'Register',
        denyButtonText: 'Cancel',
        denyButtonColor: '#9fa6b2',
    }).then((request) => {
        if (request.isConfirmed) {
            this.UserService.registerForTempGuest().subscribe(
              response => {
                if (response.success) {
                  this.router.navigate(['/update']);
                }
              }
            );
        }
        if (request.isDenied) {
            this.router.navigate(['/register']);
        }
    })
    }
  }

}
