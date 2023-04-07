import { Component, OnInit } from '@angular/core';

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


  constructor() { }

  ngOnInit(): void {
  }

  toggleSignUpForm(): void {
    this.signUpForm = !this.signUpForm;
  }



}


