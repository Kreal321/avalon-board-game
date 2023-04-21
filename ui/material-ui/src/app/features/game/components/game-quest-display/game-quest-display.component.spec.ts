import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameQuestDisplayComponent } from './game-quest-display.component';

describe('GameQuestDisplayComponent', () => {
  let component: GameQuestDisplayComponent;
  let fixture: ComponentFixture<GameQuestDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameQuestDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameQuestDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
