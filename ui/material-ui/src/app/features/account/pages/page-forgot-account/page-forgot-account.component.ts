import { Component } from '@angular/core';

@Component({
  selector: 'app-page-forgot-account',
  templateUrl: './page-forgot-account.component.html',
  styleUrls: ['./page-forgot-account.component.css']
})
export class PageForgotAccountComponent {

  token: string = '';
  username: string = '';
  userHash: string = '';
  email: string = '';
  preferredName: string = '';
  code: string = '';

}
