import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageForgotAccountComponent } from './page-forgot-account.component';

describe('PageForgotAccountComponent', () => {
  let component: PageForgotAccountComponent;
  let fixture: ComponentFixture<PageForgotAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageForgotAccountComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageForgotAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
