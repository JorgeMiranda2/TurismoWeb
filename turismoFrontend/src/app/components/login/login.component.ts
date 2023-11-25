import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObtainHeader } from '../../helpers/obtainHeader';
import { BASE_BACKEND_URL } from '../../enviroment';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private http: HttpClient,private obtainHeader:ObtainHeader,private cookieService: CookieService, private router: Router) { }
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
        this.cookieService.set("isAuth",String(true));
        this.cookieService.set("username",response.userName);
        this.router.navigate(['/']).then(()=>{
          window.location.reload();
        })

   
        console.log(response);
      },
      (error) => {

        console.log(error);
      })
  }
}
