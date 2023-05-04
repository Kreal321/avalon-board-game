import { Component, Input, OnChanges } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/core/models/user.model';
import { UserService } from 'src/app/core/services/user.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnChanges{

  @Input() user: User | undefined;
  username: string = '';
  userHash: string = '';
  email: string = '';
  preferredName: string = '';
  oneTimePassword: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnChanges(): void {
    if (this.user) {
      this.username = this.user.username;
      this.userHash = this.user.userHash;
      this.email = this.user.email;
      this.preferredName = this.user.preferredName;
      this.oneTimePassword = this.user.oneTimePassword;
    }
  }

  register(): void {
    this.userService.update(this.username, this.userHash, this.email, this.preferredName).subscribe(
      response => {
        if (response.success) {
          Swal.fire({
            title: 'Update Successful',
            icon: 'success',
            confirmButtonText: 'Continue',
          }).then(() => {
            this.router.navigate(['/profile']);
          })
        }
      }
    );
  }
}
