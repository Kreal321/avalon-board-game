import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../../../core/services/user.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'


@Component({
  selector: 'app-login-or-signup',
  templateUrl: './login-or-signup.component.html',
  styleUrls: ['./login-or-signup.component.css']
})
export class LoginOrSignupComponent implements OnInit {

  @Input() signUpForm: boolean = false;
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

  ngOnInit(): void {
  }

  toggleSignUpForm(): void {
    this.signUpForm = !this.signUpForm;
  }

  loginWithCredentials(): void {
    this.userService.loginWithCredentials(this.username, this.userHash, this.code).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Login Successful',
            text: 'Welcome back ' + response.data.preferredName,
            icon: 'success',
            confirmButtonText: 'Continue',
          }).then(() => {
            this.router.navigate(['/game']);
          })
        }
      }
    );
  }

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


