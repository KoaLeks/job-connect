import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class EditGuard implements CanActivate {
  // tslint:disable-next-line:max-line-length
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.getUserRole() === 'EMPLOYER') {
      this.router.navigate(['/edit-employer']);
      return true;
    } else if (this.authService.getUserRole() === 'EMPLOYEE') {
      this.router.navigate(['/edit-employee']);
      return true;
    } else {
      return false;
    }
  }

  constructor(private router: Router, private authService: AuthService) {

  }

}
