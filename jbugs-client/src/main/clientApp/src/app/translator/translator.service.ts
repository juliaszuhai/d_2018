import {Injectable} from '@angular/core';
import {HttpClient} from "../../../node_modules/@angular/common/http";
import {Observable} from "rxjs/Observable";


@Injectable({
  providedIn: 'root'
})


export class TranslatorService {
  public json: Observable<Object>;
  public switch: boolean;
  baseURL = 'http://localhost:8080/jbugs/rest/language/';


  constructor(private http: HttpClient) {
    this.switch = false;
  }

  public getLanguageFile(id: number) {
    return this.http.get(`${this.baseURL}${id}`);
  }

  public getTranslationObservable(id: number): Observable<Object> {
    this.json = this.getLanguageFile(id);
    return this.json;
  }


}
