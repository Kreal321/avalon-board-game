import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerBadgeComponent } from './player-badge.component';

describe('PlayerBadgeComponent', () => {
  let component: PlayerBadgeComponent;
  let fixture: ComponentFixture<PlayerBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayerBadgeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayerBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
