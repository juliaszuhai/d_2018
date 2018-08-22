import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {JwtHelperService} from '@auth0/angular-jwt';





@Injectable({
  providedIn: 'root'
})



export class TranslatorService {

  baseURL = 'http://localhost:8080/jbugs/rest/index/';

  constructor(private http: HttpClient) {
  }



  getLanguageId(id: number) {
        return this.http.get(`${this.baseURL}${id}`)
          .subscribe(lang => console.log(lang));

    // Test
   //return this.http.get(this.baseURL,  );

  }

}
