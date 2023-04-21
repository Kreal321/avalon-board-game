import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-vote-container',
  templateUrl: './vote-container.component.html',
  styleUrls: ['./vote-container.component.css']
})
export class VoteContainerComponent implements OnChanges{

  @Input() status: string = "processing";
  @Input() voteType: string = "Team Vote";

  color: string = "primary";
  title: string = "Processing";

  constructor() { }

  ngOnChanges(): void {

    if (this.voteType == "mission") {
      this.title = "Mission";
    } else {
      this.title = "Team Vote";
    }
    
    switch(this.status) {
      case "success":
        this.color = "success";
        this.title += " Succeeded";
        break;
      case "fail":
        this.color = "danger";
        this.title += " Failed";
        break;
      default:
        this.color = "primary";
        this.title += " In Progress";
        break;
    }

  }

}
