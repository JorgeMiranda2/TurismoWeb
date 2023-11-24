import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TouristDestinationModalComponent } from './tourist-destination-modal.component';

describe('TouristDestinationModalComponent', () => {
  let component: TouristDestinationModalComponent;
  let fixture: ComponentFixture<TouristDestinationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TouristDestinationModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TouristDestinationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
