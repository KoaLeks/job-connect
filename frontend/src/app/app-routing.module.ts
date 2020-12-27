import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EditGuard} from './guards/edit.guard';
import {EditEmployerComponent} from './components/edit-employer/edit-employer.component';
import {EditEmployeeComponent} from './components/edit-employee/edit-employee.component';
import {EditProfileComponent} from './components/edit-profile/edit-profile.component';
import {EventOverviewComponent} from './components/event-overview/event-overview.component';
import {CreateEventComponent} from './components/create-event/create-event.component';
import {EventDetailsComponent} from './components/event-details/event-details.component';
import {EmployeeOverviewComponent} from './components/employee-overview/employee-overview.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {EmployeeDetailsComponent} from './components/employee-details/employee-details.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'events', component: EventOverviewComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'create-event', component: CreateEventComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'edit-profile', canActivate: [EditGuard], component: EditProfileComponent},
  {path: 'edit-employee', canActivate: [AuthGuard], component: EditEmployeeComponent},
  {path: 'edit-employer', canActivate: [AuthGuard], component: EditEmployerComponent},
  {path: 'events/:id/details', component: EventDetailsComponent},
  {path: 'employee-overview', canActivate: [AuthGuard], component: EmployeeOverviewComponent},
  {path: 'employee/:id/details', canActivate: [AuthGuard], component: EmployeeDetailsComponent},
  {path: '**', component: PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
