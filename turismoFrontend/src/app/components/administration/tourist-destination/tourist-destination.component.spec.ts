import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { TouristDestinationComponent } from './tourist-destination.component';

describe('TouristDestinationComponent', () => {
  let component: TouristDestinationComponent;
  let fixture: ComponentFixture<TouristDestinationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TouristDestinationComponent,MatSlideToggleModule]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TouristDestinationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
