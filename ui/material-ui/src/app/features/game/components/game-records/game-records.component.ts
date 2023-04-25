import { Component, OnInit } from '@angular/core';

import { Record } from 'src/app/core/models/record.model';
import { UserService } from 'src/app/core/services/user.service';


@Component({
  selector: 'app-game-records',
  templateUrl: './game-records.component.html',
  styleUrls: ['./game-records.component.css']
})
export class GameRecordsComponent implements OnInit {

  showRecords: boolean = false;
  records: Record[] = [];

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {

    this.userService.getRecords().subscribe(
      response => {
        if (response.success) {
          this.records = response.data;
        }
      }
    )
  }

}
