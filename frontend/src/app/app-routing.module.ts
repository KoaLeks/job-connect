import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EditGuard} from './guards/edit.guard';
import {EditEmployerComponent} from './components/edit-employer/edit-employer.component';
import {EditEmployeeComponent} from './components/edit-employee/edit-employee.component';
import {EditProfileComponent} from './components/edit-profile/edit-profile.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'edit-profile', canActivate: [EditGuard], component: EditProfileComponent},
  {path: 'edit-employee', component: EditEmployeeComponent},
  {path: 'edit-employer', component: EditEmployerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
