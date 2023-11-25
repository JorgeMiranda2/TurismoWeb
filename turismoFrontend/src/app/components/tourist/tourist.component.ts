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
  }




  plans: any[] = [];




selectedData: any | null = null;
isUpdate: boolean = false;

getUniqueLodgings(touristDestinations: any[]): string[] {
  const uniqueLodgings = new Set<string>();
  touristDestinations.forEach(destination => {
    if (destination.city && destination.city.lodgings) {
      destination.city.lodgings.forEach((lodging:any) => {
        uniqueLodgings.add(lodging.name);
      });
    }
  });
  return Array.from(uniqueLodgings);
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
