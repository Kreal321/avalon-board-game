import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/models/user.model';

import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-page-profile',
  templateUrl: './page-profile.component.html',
  styleUrls: ['./page-profile.component.css']
})
export class PageProfileComponent implements OnInit{

  me : User | undefined;

  constructor(
    private UserService: UserService
  ) { }

  ngOnInit(): void {
    this.UserService.me().subscribe(
      response => {
        if (response.success) {
          this.me = response.data;
        }
      }
    );
  }

}
