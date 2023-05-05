import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageRegisterTempComponent } from './page-register-temp.component';

describe('PageRegisterTempComponent', () => {
  let component: PageRegisterTempComponent;
  let fixture: ComponentFixture<PageRegisterTempComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageRegisterTempComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageRegisterTempComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
