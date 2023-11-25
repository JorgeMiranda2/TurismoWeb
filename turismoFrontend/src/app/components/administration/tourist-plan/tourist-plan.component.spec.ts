import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TouristPlanComponent } from './tourist-plan.component';

describe('TouristPlanComponent', () => {
  let component: TouristPlanComponent;
  let fixture: ComponentFixture<TouristPlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TouristPlanComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TouristPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
