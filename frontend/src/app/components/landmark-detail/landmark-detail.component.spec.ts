import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandmarkDetailComponent } from './landmark-detail.component';

describe('LandmarkDetailComponent', () => {
  let component: LandmarkDetailComponent;
  let fixture: ComponentFixture<LandmarkDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandmarkDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandmarkDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
