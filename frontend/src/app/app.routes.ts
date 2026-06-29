import { Routes } from '@angular/router';
import { LandmarkListComponent } from './components/landmark-list/landmark-list.component';
import { LandmarkDetailComponent } from './components/landmark-detail/landmark-detail.component';
import { LandmarkSearchComponent } from './components/landmark-search/landmark-search.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';

export const routes: Routes = [
  { path: '', component: LandmarkListComponent },
  { path: 'landmarks/:id', component: LandmarkDetailComponent },
  { path: 'search', component: LandmarkSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
];
