import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EditEmployerComponent} from './components/edit-employer/edit-employer.component';
import {EditEmployeeComponent} from './components/edit-employee/edit-employee.component';
import {EventOverviewComponent} from './components/event-overview/event-overview.component';
import {CreateEventComponent} from './components/create-event/create-event.component';
import {EventDetailsComponent} from './components/event-details/event-details.component';
import {EmployeeOverviewComponent} from './components/employee-overview/employee-overview.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {EmployeeDetailsComponent} from './components/employee-details/employee-details.component';
import {EmployerGuard} from './guards/employer.guard';
import {EmployeeGuard} from './guards/employee.guard';
import {EmployerDetailsComponent} from './components/employer-details/employer-details.component';
import {EventAppliedComponent} from './components/event-applied/event-applied.component';
import {ExpiredEventsComponent} from './components/expired-events/expired-events.component';
import {PastAppliedEventsComponent} from './components/past-applied-events/past-applied-events.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'events', component: EventOverviewComponent},
  {path: 'employer/:id/details', component: EmployerDetailsComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'create-event', canActivate: [AuthGuard, EmployerGuard], component: CreateEventComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'edit-employee', canActivate: [AuthGuard, EmployeeGuard], component: EditEmployeeComponent},
  {path: 'edit-employer', canActivate: [AuthGuard, EmployerGuard], component: EditEmployerComponent},
  {path: 'events/:id/details', component: EventDetailsComponent},
  {path: 'employee-overview', canActivate: [AuthGuard, EmployerGuard], component: EmployeeOverviewComponent},
  {path: 'expired-events', canActivate: [AuthGuard, EmployerGuard], component: ExpiredEventsComponent},
  {path: 'employee/:id/details', canActivate: [AuthGuard, EmployerGuard], component: EmployeeDetailsComponent},
  {path: 'applied-events', canActivate: [AuthGuard, EmployeeGuard], component: EventAppliedComponent},
  {path: 'past-applied-events', canActivate: [AuthGuard, EmployeeGuard], component: PastAppliedEventsComponent},
  {path: '**', component: PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
