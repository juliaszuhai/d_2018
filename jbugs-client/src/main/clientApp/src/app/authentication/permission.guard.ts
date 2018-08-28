import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from "./authentication.service";

@Injectable(
)
export class PermissionGuard implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {


    if (this.authService.isLoggedIn()) {
      if (this.authService.userHasPermission("USER_MANAGEMENT")) {

        return true;
      } else {
        console.log("hellow");
        this.router.navigate(['/profile'], {
          queryParams: {
            return: state.url
          }
        });
        return false;
      }

    } else {
      this.router.navigate(['/login'], {
        queryParams: {
          return: state.url
        }
      });
      return false;
    }
  }
}
