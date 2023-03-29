import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameJoinOrCreateComponent } from './game-join-or-create.component';

describe('GameJoinOrCreateComponent', () => {
  let component: GameJoinOrCreateComponent;
  let fixture: ComponentFixture<GameJoinOrCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameJoinOrCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameJoinOrCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
