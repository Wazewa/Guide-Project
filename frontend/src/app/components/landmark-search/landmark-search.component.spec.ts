import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandmarkSearchComponent } from './landmark-search.component';

describe('LandmarkSearchComponent', () => {
  let component: LandmarkSearchComponent;
  let fixture: ComponentFixture<LandmarkSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandmarkSearchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandmarkSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
