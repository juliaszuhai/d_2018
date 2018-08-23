import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import * as moment from "moment";
import _date = moment.unitOfTime._date;
import {UserData} from "../authentication/authentication.service";
import {st} from "@angular/core/src/render3";


export interface RelatedUser {
  id: number;
  username: string;
}

export interface BugData {
  title: string;
  description: string;
  version: string;
  targetDate: Date;
  status: string;
  fixedVersion: string;
  severity: string;
  createdByUser: RelatedUser;
  assignedTo: RelatedUser;
}

const bugs: BugData[] = [];

@Injectable({
  providedIn: 'root'
})
export class BugListService {

  baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private http: HttpClient) {
  }

  getBugList(): Observable<BugData> {
    return from(bugs);
  }

  getBugsFromServer(): Observable<BugData[]> {
    return this.http.get<BugData[]>(this.baseURL + '/listBugs', {
      // params: new HttpParams().set('dummyParam', 'dummyvalue')
    });
  }


  filter(title: string, description: string, status: string, severity: string): Observable<BugData[]> {
    let params = new HttpParams();

    if (title) {
      params = params.append('title', title);
    }
    if (description) {
      params = params.append('description', description);
    }
    if (status) {
      params = params.append('status', status.toUpperCase());
    }
    if (severity) {
      params = params.append('severity', severity.toUpperCase());
    }


    return this.http.get<BugData[]>(this.baseURL + '/listBugs/getByFilter', {params: params});
  }

}
