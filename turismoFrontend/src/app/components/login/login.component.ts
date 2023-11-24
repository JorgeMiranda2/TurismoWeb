import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObtainHeader } from '../../helpers/obtainHeader';
import { BASE_BACKEND_URL } from '../../enviroment';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private http: HttpClient,private obtainHeader:ObtainHeader,private cookieService: CookieService) { }
  username: string = '';
  password: string = '';

  login(): void {
    const headers = this.obtainHeader.getAuthHeader();
    const send = {
        userName:this.username,
        password:this.password
    }

     this.http
      .post<any>(`${BASE_BACKEND_URL}/auth/login`, send,{ headers }).subscribe((response) => {
        this.cookieService.set("token",response.token);
        console.log(response);
      },
      (error) => {

        console.log(error);
      })
  }
}
