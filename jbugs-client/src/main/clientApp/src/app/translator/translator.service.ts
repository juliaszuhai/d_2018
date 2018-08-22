import { Injectable } from '@angular/core';
import {HttpClient} from "../../../node_modules/@angular/common/http";
import {Router} from "@angular/router";


@Injectable({
  providedIn: 'root'
})


export class TranslatorService {

  baseURL = 'http://localhost:8080/jbugs/rest/language/';
  constructor(private http: HttpClient,private  router: Router) { }

  public getLanguageFile(id:number){
      return this.http.get(`${this.baseURL}${id}`)

        .subscribe(lng => console.log(lng));

  }
}
