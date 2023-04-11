import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageGameDashboardComponent } from './page-game-dashboard.component';

describe('PageGameDashboardComponent', () => {
  let component: PageGameDashboardComponent;
  let fixture: ComponentFixture<PageGameDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageGameDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageGameDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
