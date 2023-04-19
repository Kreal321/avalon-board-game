import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageGameHomeComponent } from './page-game-home.component';

describe('PageGameHomeComponent', () => {
  let component: PageGameHomeComponent;
  let fixture: ComponentFixture<PageGameHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageGameHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageGameHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
