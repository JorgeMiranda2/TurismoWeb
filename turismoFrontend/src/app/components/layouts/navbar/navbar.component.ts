import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {
  isAuth:boolean = false;
  username:String = "";
  constructor(private router: Router,private cookieService: CookieService) {
    this.isAuth = cookieService.get('isAuth') === 'true';
    this.username = cookieService.get('username');
  }


  navigateToLoginPage() {
    this.router.navigate(['/login']);
  }

  goToTouristPlans(){
    this.router.navigate(['/tourist']);
  }
  logout(){
   this.cookieService.set('isAuth',String(false));
    this.cookieService.set('username', "");
    this.cookieService.set('token',"");
      window.location.reload();
  }
}
