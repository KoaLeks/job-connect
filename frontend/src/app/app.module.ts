import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { EditEmployerComponent } from './components/edit-employer/edit-employer.component';
import { EditEmployeeComponent } from './components/edit-employee/edit-employee.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { EventOverviewComponent } from './components/event-overview/event-overview.component';
import { RegisterComponent } from './components/register/register.component';
import { EditPasswordComponent } from './components/edit-password/edit-password.component';
import {UpdateHeaderService} from './services/update-header.service';
import { EventDetailsComponent } from './components/event-details/event-details.component';
import { EmployeeOverviewComponent } from './components/employee-overview/employee-overview.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    EditEmployerComponent,
    EditEmployeeComponent,
    EditProfileComponent,
    EventOverviewComponent,
    CreateEventComponent,
    RegisterComponent,
    EditPasswordComponent,
    EventDetailsComponent,
    EmployeeOverviewComponent,
    NotificationListComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule
  ],
  providers: [httpInterceptorProviders, UpdateHeaderService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
