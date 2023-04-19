import { Component, EventEmitter, Input, Output } from '@angular/core';


@Component({
  selector: 'app-game-join',
  templateUrl: './game-join.component.html',
  styleUrls: ['./game-join.component.css']
})
export class GameJoinComponent {

  @Input() showCreationForm: boolean = false;
  @Output() showCreationFormChange = new EventEmitter<boolean>();

  toggleCreationForm() : void {
    this.showCreationForm = !this.showCreationForm;
    this.showCreationFormChange.emit(this.showCreationForm);
  }

}
