import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-login-or-signup',
  templateUrl: './login-or-signup.component.html',
  styleUrls: ['./login-or-signup.component.css']
})
export class LoginOrSignupComponent implements OnInit {

  signUpForm: boolean = true;
  token: string = '';
  username: string = '';
  userHash: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';


  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
  }

  toggleSignUpForm(): void {
    this.signUpForm = !this.signUpForm;
  }

  loginWithCredentials(): void {
    this.userService.loginWithCredentials(this.username, this.userHash, this.code).subscribe(
      response => {
        console.log(response);
      }
    );
  }



}


