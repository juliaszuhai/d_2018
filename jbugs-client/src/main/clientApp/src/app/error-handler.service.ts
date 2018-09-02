import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpRequest} from "@angular/common/http";
import {Observable, of} from "rxjs/index";
import {catchError} from "rxjs/operators";
import {ErrorViewComponent} from "./error-view/error-view.component";
import {MatSnackBar} from "@angular/material";

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(public snackBar: MatSnackBar) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


    return next.handle(request).pipe(catchError((error, caught) => {
      // intercept the respons error and displace it to the console
      this.handleError(error);
      return of(error);
    }) as any);
  }

  openSnackBar(message: string) {
    this.snackBar.openFromComponent(ErrorViewComponent, {
      duration: 500,
    })
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.error);
    this.openSnackBar(err.error);

  }
}
