import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ObtainHeader } from '../../../helpers/obtainHeader';
import { HttpClient } from '@angular/common/http';
import { BASE_BACKEND_URL } from '../../../enviroment';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-lodging-modal',
  standalone: true,
  imports: [CommonModule,FormsModule,MatFormFieldModule, MatSelectModule,ReactiveFormsModule],
  templateUrl: './lodging-modal.component.html',
  styleUrl: './lodging-modal.component.css'
})
export class LodgingModalComponent {



  constructor(private http: HttpClient,@Inject(MAT_DIALOG_DATA) public data: any, private obtainHeader: ObtainHeader) {}

  cities : any[] = []

  lodging = {
    name: '',
    numRooms:null,
    lodgingType: null,
    checkIn: '', 
    checkOut: '',
    cityId:null 

  };

  lodgingId = null;
  cityName = null;


  lodgingTypes = null;
  selecDestinations = new FormControl('');
  selectedCity = null;

  ngOnInit() {

    this.lodgingId=this.data.selectedData?.lodging.id;
    this.cityName = this.data.selectedData?.city.name;
    const headers = this.obtainHeader.getAuthHeader();
this.http
      .get<any>(`${BASE_BACKEND_URL}/api/lodgingtypes`, { headers }).subscribe((res)=>{
        console.log(res);
        this.lodgingTypes = res.lodgingTypes;
    

        if(this.data.selectedData){
          this.lodging = {
            name: this.data.selectedData?.lodging.name,
            numRooms:this.data.selectedData?.lodging.numRooms,
            lodgingType: this.data.selectedData?.lodging.lodgingType,
            checkIn: this.data.selectedData?.lodging.checkIn, 
            checkOut: this.data.selectedData?.lodging.checkOut ,
            cityId:this.data.selectedData?.city.id
          }
        }
       
      },
      (error)=>{
        console.log("error: " , error)
      })

      this.http
      .get<any>(`${BASE_BACKEND_URL}/api/cities`, { headers }).subscribe((res)=>{
        console.log(res);
      this.cities = res;
       
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
    this.submitData.emit(this.lodging);
    this.onCloseModal();
  }
}
