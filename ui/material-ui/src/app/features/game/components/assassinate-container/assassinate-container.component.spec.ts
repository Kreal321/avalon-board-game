import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssassinateContainerComponent } from './assassinate-container.component';

describe('AssassinateContainerComponent', () => {
  let component: AssassinateContainerComponent;
  let fixture: ComponentFixture<AssassinateContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssassinateContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssassinateContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
