import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageGameRoomComponent } from './page-game-room.component';

describe('PageGameRoomComponent', () => {
  let component: PageGameRoomComponent;
  let fixture: ComponentFixture<PageGameRoomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageGameRoomComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageGameRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
