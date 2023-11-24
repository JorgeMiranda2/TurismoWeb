import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { TouristComponent } from './components/tourist/tourist.component';
import { RegisterComponent } from './components/register/register.component';
import { AdministrationComponent } from './components/administration/administration.component';
import { TouristDestinationComponent } from './components/administration/tourist-destination/tourist-destination.component';


const routes: Routes = [
    { path: '', component:HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component:RegisterComponent },
    { path: 'tourist', component: TouristComponent },
    { path: 'administration', component: AdministrationComponent,
    children:[
        { path: 'touristdestination', component:TouristDestinationComponent },
    ]
}
    // ... otras rutas
  ];

  export {routes};
