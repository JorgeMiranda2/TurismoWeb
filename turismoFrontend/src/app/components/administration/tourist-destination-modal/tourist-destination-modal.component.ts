import { Component, OnInit, Output,EventEmitter, Input, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {IUbications} from "../../../interfaces/IUbications";
import { BASE_BACKEND_URL } from '../../../enviroment';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ObtainHeader } from '../../../helpers/obtainHeader';


@Component({
  selector: 'app-tourist-destination-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tourist-destination-modal.component.html',
  styleUrl: './tourist-destination-modal.component.css'
})
export class TouristDestinationModalComponent implements OnInit {
  
 


  constructor(private http: HttpClient,@Inject(MAT_DIALOG_DATA) public data: any, private obtainHeader: ObtainHeader) {}
  datoModal = this.data.datoModal;

  ubications:IUbications = {
    countries: [],
    departments: [],
    cities: []
};



 loadUbications(): Observable<IUbications> {
    const headers = this.obtainHeader.getAuthHeader();


    return this.http
      .get<IUbications>(`${BASE_BACKEND_URL}/api/ubications`, { headers })
  
  }

  selectedCountry: Number | null = null;
  selectedDepartment: Number | null = null;
  selectedCity: Number | null = null;
  destinationName: string = '';
  countries: any[] = []; 
  departments: any[] = []; 
  cities: any[] = []; 

 
  ngOnInit() {
   this.loadUbications().subscribe(
    (response) => {
      console.log(response);
      this.ubications = response;


      if (this.data.selectedData) {
       this.selectedCity = this.data.selectedData.city.id;
       // Encontrar el departamento asociado al ID de la ciudad seleccionada
       const city = this.ubications.cities.find(city => city.id === this.selectedCity);
       if (city) {
         const departmentId = city.departmentId;
         // Encontrar el país asociado al ID del departamento
         const department = this.ubications.departments.find(department => department.id === departmentId);
         if (department) {
           const countryId = department.countryId;
           // Aquí tendrás el ID del país asociado y el ID del departamento asociado a la ciudad seleccionada
           console.log('ID del país asociado:', countryId);
           console.log('ID del departamento asociado:', departmentId);
     
           // Asigna estos valores a las variables selectedCountry y selectedDepartment
           this.selectedCountry = countryId;
           this.selectedDepartment = departmentId;
           this.destinationName = this.data.selectedData.name;
         }
       }
   
     }
    },
    (error) => {
      console.log(error);
    }
   )

 
  }





  onCountryChange() {
    // Lógica para obtener los departamentos asociados al país seleccionado
    this.departments = this.ubications.departments.filter((dep) => dep.countryId === this.selectedCountry);
    this.selectedDepartment = null;
    this.cities = [];
  }

  onDepartmentChange() {
    // Lógica para obtener las ciudades asociadas al departamento seleccionado
    this.cities = this.ubications.cities.filter((city) => city.departmentId === this.selectedDepartment);
    this.selectedCity = null;
  }


  


  @Output() closeModal: EventEmitter<void> = new EventEmitter<void>();
  @Output() submitData: EventEmitter<any> = new EventEmitter<any>();

  onCloseModal() {
    
    this.closeModal.emit();
  }
  onSubmit() {
    const dataToSend = {
      name: this.destinationName,
      cityId: this.selectedCity
    };
    
    this.submitData.emit(dataToSend);
    this.onCloseModal();
  }
}
