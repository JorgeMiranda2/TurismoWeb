import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LodgingModalComponent } from './lodging-modal.component';

describe('LodgingModalComponent', () => {
  let component: LodgingModalComponent;
  let fixture: ComponentFixture<LodgingModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LodgingModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LodgingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
