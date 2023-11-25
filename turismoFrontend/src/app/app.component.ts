import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router } from '@angular/router';
import { NavbarComponent } from './components/layouts/navbar/navbar.component';




@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})


export class AppComponent {
  constructor(private router: Router) {}

  title = 'turismoFrontend';

  navigateToLoginPage() {
    console.log("using login");
    this.router.navigate(['/login']);
  }
}
