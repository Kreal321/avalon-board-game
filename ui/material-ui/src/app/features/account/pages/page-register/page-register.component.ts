import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-page-register',
  templateUrl: './page-register.component.html',
  styleUrls: ['./page-register.component.css']
})
export class PageRegisterComponent {

  token: string = '';
  username: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {
    // Swal.fire({
    //   title: 'Registration is not open yet',
    //   text: 'Register as a guest to play',
    //   icon: 'info',
    //   confirmButtonText: 'Continue',
    // }).then(() => {
    //   this.router.navigate(['/register/temp']);
    // })
  }

}
