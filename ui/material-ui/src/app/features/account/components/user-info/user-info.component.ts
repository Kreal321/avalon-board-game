import { Component, Input } from '@angular/core';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent {

  @Input() user: User | undefined;

  copyToClipboard() {
    navigator.clipboard.writeText(btoa(`${this.user?.username}:${this.user?.oneTimePassword}`));
  }

}
