import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BASE_BACKEND_URL } from '../../enviroment';
import { ObtainHeader } from '../../helpers/obtainHeader';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import {MatExpansionModule} from '@angular/material/expansion';
@Component({
  selector: 'app-tourist',
  standalone: true,
  imports: [CommonModule, MatExpansionModule, HttpClientModule],
  templateUrl: './tourist.component.html',
  styleUrl: './tourist.component.css'
})
export class TouristComponent {

  unlinkedPlans: any[] = []; 
  linkedPlans: any[] = []; 


  constructor(private http: HttpClient, private obtainHeader : ObtainHeader) {}
  panelOpenState = false;

 loadPlans(): Observable<any[]> {
    const headers = this.obtainHeader.getAuthHeader();

    return this.http
      .get<any[]>(`${BASE_BACKEND_URL}/api/quotastouristplan`, { headers })
  
  }

  ngOnInit() {
   this.loadPlans().subscribe(
    (response) => {
      console.log(response);
      this.plans=response;
    },
    (error) => {
      console.log(error);
    }
   )

   this.loadUserPlans().subscribe(
    (response) => {
      console.log(response);
    this.unlinkedPlans = response.unlinkedPlans;
    this.linkedPlans = response.linkedPlans;

    },
    (error) => {
      console.log(error);
    }
   )
  }




  plans: any[] = [];


selectedData: any | null = null;
isUpdate: boolean = false;


loadUserPlans(): Observable<any> {
  const headers = this.obtainHeader.getAuthHeader();

  return this.http
    .get<any[]>(`${BASE_BACKEND_URL}/api/plansbyuser`, { headers })

}

getAvailablePlan(id:any){
  const headers = this.obtainHeader.getAuthHeader();
  this.http
  .get<any>(`${BASE_BACKEND_URL}/api/vinculatetouristplan/${id}`,{ headers }).subscribe((res)=>{
    console.log(res);
    this.loadPlans().subscribe((response)=>{
      this.plans=response;
    }, (error)=>{
      console.log("error: " , error);
    });
  },
  (error) => {
    console.error('error:', error);

  })
}

}
