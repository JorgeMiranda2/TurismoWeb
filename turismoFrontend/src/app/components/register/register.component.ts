import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BASE_BACKEND_URL } from '../../enviroment';
import { ObtainHeader } from '../../helpers/obtainHeader';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,FormsModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private http: HttpClient, private obtainHeader: ObtainHeader, private router: Router) {}
  registerData = {
    email: '',
    name: '',
    lastName: '',
    userName: '',
    password: ''
  };



  register() {
    const headers = this.obtainHeader.getAuthHeader();
    this.http
    .post<any>(`${BASE_BACKEND_URL}/auth/register`, this.registerData, { headers }).subscribe((res)=>{
      console.log(res);
      this.router.navigate(['/login']).then(()=>{
        window.location.reload();
      })
this.router
    },
    (error)=>{
      console.log("error: " , error)
    })
 
  }
}
