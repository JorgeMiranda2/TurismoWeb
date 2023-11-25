import { Component, Inject, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ObtainHeader } from '../../../helpers/obtainHeader';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BASE_BACKEND_URL } from '../../../enviroment';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-tourist-plan-modal',
  standalone: true,
  imports: [CommonModule,FormsModule,MatFormFieldModule, MatSelectModule,ReactiveFormsModule],
  templateUrl: './tourist-plan-modal.component.html',
  styleUrl: './tourist-plan-modal.component.css'
})
export class TouristPlanModalComponent {



  constructor(private http: HttpClient,@Inject(MAT_DIALOG_DATA) public data: any, private obtainHeader: ObtainHeader) {}


  touristPlan = {
    name: '',
    price: null,
    days: null,
    nights: null,
    transportType: '',
    numEnabledPackages: null,
    touristDestinationIds: []
  };

  transports = null;
  destinations = null;
  selecDestinations = new FormControl('');

  ngOnInit() {

    const headers = this.obtainHeader.getAuthHeader();
this.http
      .get<any>(`${BASE_BACKEND_URL}/api/touristplansbasics`, { headers }).subscribe((res)=>{
        console.log(res);
        this.transports = res.transportTypes;
        this.destinations = res.destinations;


        if(this.data.selectedData){
          this.touristPlan = {
            name: this.data.selectedData?.name,
            price: this.data.selectedData?.price,
            days: this.data.selectedData?.days,
            nights: this.data.selectedData?.nights,
            transportType: this.data.selectedData?.transportType,
            numEnabledPackages: this.data.selectedData?.numEnabledPackages,
            touristDestinationIds: this.data.selectedData?.touristdestinations
          }
            // Establecer los valores iniciales seleccionados en el FormControl
            const initialValues = this.data.selectedData?.touristDestinations
            const initialSelectedDestinations = initialValues.map((touristdestination:any)=>{
              return touristdestination.id;
            })
    
            this.selecDestinations.patchValue(initialSelectedDestinations);
        }
       



      },
      (error)=>{
        console.log("error: " , error)
      })
   
       }
   


  @Output() closeModal: EventEmitter<void> = new EventEmitter<void>();
  @Output() submitData: EventEmitter<any> = new EventEmitter<any>();

  onCloseModal() {
    
    this.closeModal.emit();
  }
  onSubmit() {    
    const sendData = {...this.touristPlan, touristDestinationIds:this.selecDestinations.value}
    this.submitData.emit(sendData);
    this.onCloseModal();
  }
}
