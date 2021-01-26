import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpEventType
} from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import {tap} from 'rxjs/operators';
import {AlertService} from '../alert';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(protected alertService: AlertService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      tap(
        (sentEvent) => {
          if (sentEvent instanceof HttpResponse) {
            if (sentEvent.status === 201) {
              this.alertService.success('Erfolgreich erstellt', {autoClose: true, keepAfterRouteChanges: false});
            }
            if (sentEvent.status === 204) {
              this.alertService.success('Erfolgreich', {autoClose: true, keepAfterRouteChanges: false}) ;
            }
          }
        },
      (error) => {
          this.alertService.clear();
          if (error.status === 404) {
            this.alertService.error(error.error, {autoClose: false, keepAfterRouteChanges: true});
            return;
          }
          if (error.error === 'Bad credentials') { return throwError(error.message); }
          if (error.status === 500 || error.status === 0 ) {
            this.alertService.error('Interner Fehler: Bitte versuchen Sie es später erneut', {autoClose: false, keepAfterRouteChanges: true});
            return ;
          }
          this.alertService.error(error.error, {autoClose: false, keepAfterRouteChanges: true});
          return throwError(error.message);
        }
      )
    );
  }
}
