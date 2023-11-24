import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BASE_BACKEND_URL } from '../enviroment';
import { ITouristDestination } from '../interfaces/ITouristDestination';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class TouristDestinationService {

  constructor(private http: HttpClient,private cookieService: CookieService) {}

  private getAuthHeader(): HttpHeaders {
    const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGaW5lc2xvdyIsImlhdCI6MTcwMDc4NjQ2MywiZXhwIjoxNzAwODE2NDYzfQ.-geWpbvjS1wwDM-SVOp4tABNRxT1PohTfu07vVLJzUA";

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  loadDestinations(): Observable<ITouristDestination[]> {
    const headers = this.getAuthHeader();

    return this.http.get<ITouristDestination[]>(`${BASE_BACKEND_URL}/api/touristdestination`, { headers });
  }
}
