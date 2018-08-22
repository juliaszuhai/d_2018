import { Injectable } from '@angular/core';
import {HttpClient} from "../../../node_modules/@angular/common/http";
import {Locale} from "moment";
import {Router} from "@angular/router";
import {Http} from "@angular/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class TranslatorService {
  people:String="";
  baseURL = 'http://localhost:8080/jbugs/rest/language/';
  constructor(private http: HttpClient,private  router: Router) { }

  public getLocal(id:number){
      return this.http.get(`${this.baseURL}${id}`)

        .subscribe(people => console.log(people));

  }
}
