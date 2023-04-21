import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameRoundComponent } from './game-round.component';

describe('GameRoundComponent', () => {
  let component: GameRoundComponent;
  let fixture: ComponentFixture<GameRoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameRoundComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameRoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
