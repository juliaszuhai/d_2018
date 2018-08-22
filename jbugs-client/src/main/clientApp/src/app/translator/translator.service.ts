import { Injectable } from '@angular/core';
import {HttpClient} from "../../../node_modules/@angular/common/http";
<<<<<<< HEAD
import {Router} from "@angular/router";

=======
import {Locale} from "moment";
import {Router} from "@angular/router";
import {Http} from "@angular/http";
import {map} from "rxjs/operators";
>>>>>>> d592d75e5a6887668850991edb29673d35ffd653

@Injectable({
  providedIn: 'root'
})
<<<<<<< HEAD




=======
>>>>>>> d592d75e5a6887668850991edb29673d35ffd653
export class TranslatorService {
  people:String="";
  baseURL = 'http://localhost:8080/jbugs/rest/language/';
  constructor(private http: HttpClient,private  router: Router) { }

  public getLocal(id:number){
      return this.http.get(`${this.baseURL}${id}`)

        .subscribe(people => console.log(people));

  }
}
