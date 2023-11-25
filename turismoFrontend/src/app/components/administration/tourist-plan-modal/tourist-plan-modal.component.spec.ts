import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TouristPlanModalComponent } from './tourist-plan-modal.component';

describe('TouristPlanModalComponent', () => {
  let component: TouristPlanModalComponent;
  let fixture: ComponentFixture<TouristPlanModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TouristPlanModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TouristPlanModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
