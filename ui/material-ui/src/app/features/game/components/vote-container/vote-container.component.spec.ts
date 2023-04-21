import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteContainerComponent } from './vote-container.component';

describe('VoteContainerComponent', () => {
  let component: VoteContainerComponent;
  let fixture: ComponentFixture<VoteContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteContainerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
