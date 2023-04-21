import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameRoundsComponent } from './game-rounds.component';

describe('GameRoundsComponent', () => {
  let component: GameRoundsComponent;
  let fixture: ComponentFixture<GameRoundsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameRoundsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameRoundsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
