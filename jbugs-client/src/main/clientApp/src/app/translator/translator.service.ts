import {Injectable} from '@angular/core';
import {HttpClient} from "../../../node_modules/@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";


@Injectable({
  providedIn: 'root'
})


export class TranslatorService {
  json: Observable<Object>;
  baseURL = 'http://localhost:8080/jbugs/rest/language/';

  constructor(private http: HttpClient, private  router: Router) {
  }

  public getLanguageFile(id: number) {
    return this.http.get(`${this.baseURL}${id}`);
  }

  public getTranslationObservable(id: number): Observable<Object> {
    this.json = this.getLanguageFile(id);
    return this.json;
  }

  public getTranslationData() : Observable<Object> {
    return this.json;
  }

}
