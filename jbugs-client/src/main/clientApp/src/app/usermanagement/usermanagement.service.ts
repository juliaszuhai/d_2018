import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UsermanagementService {

  constructor(private http: HttpClient) { }
  baseURL = 'http://localhost:8080/jbugs/rest/manageusers';
  getAllUsers(){

    return this.http.get(this.baseURL + '/getallusers');
  }
}
