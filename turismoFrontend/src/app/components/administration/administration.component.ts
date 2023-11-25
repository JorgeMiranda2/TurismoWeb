import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-administration',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './administration.component.html',
  styleUrl: './administration.component.css'
})
export class AdministrationComponent {
  constructor(private router: Router) {
  }
  showComponent(route:String){
    this.router.navigate([route]);
  }
}
