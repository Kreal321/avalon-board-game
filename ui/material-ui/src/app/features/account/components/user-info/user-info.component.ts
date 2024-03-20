import { Component, Input } from '@angular/core';
import { User } from 'src/app/core/models/user.model';
import Swal from "sweetalert2";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent {

  @Input() user: User | undefined;

  copyToClipboard() {
    let token: string = btoa(`${this.user?.username}:${this.user?.oneTimePassword}`);
    Swal.fire({
      title: "Here's your token",
      text: token,
      icon: 'info',
      confirmButtonText: 'OK',
    })
    navigator.clipboard.writeText(token);
  }

}
