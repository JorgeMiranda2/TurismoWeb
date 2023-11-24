import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class ObtainHeader {
    constructor(private cookieService: CookieService) { }
  public getAuthHeader(): HttpHeaders {
  
    const token = this.cookieService.get('token') || '';
  
    console.log("local: " , token);
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

}
