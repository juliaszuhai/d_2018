import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthenticationService} from './authentication.service';
import {Router} from '@angular/router';
import {MatSnackBar} from "@angular/material";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  tokenField = 'token';
  errorString: string;
  errorMsg: string;

  constructor(public auth: AuthenticationService, private router: Router, public snackBar: MatSnackBar, public translate: TranslateService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.getItem(this.tokenField)) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ` + localStorage.getItem(this.tokenField)
        }
      });
    }

    /**
     * continues request execution
     */
    return next.handle(request).pipe(catchError((error, caught) => {
      // intercept the respons error and displace it to the console
      // console.log(error);;
      this.handleError(error);
      return of(error);
    }) as any);
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, null, {
      duration: 5000,
    })
  }

  private handleError(err: HttpErrorResponse) {
    this.errorString = 'exceptionCodes.' + err.error.value;
    console.log(err);
    this.translate.get(this.errorString).subscribe((res: string) => this.errorMsg = res);
    this.openSnackBar(this.errorMsg);

  }

}
