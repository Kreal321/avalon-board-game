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
  userHash: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  register(): void {
    this.userService.register(this.username, this.userHash, this.email, this.preferredName).subscribe(
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
