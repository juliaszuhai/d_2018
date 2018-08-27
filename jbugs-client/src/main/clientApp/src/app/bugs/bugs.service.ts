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
  id: number;
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

  getLoggedUserName():string{
    return localStorage.getItem("id_token");
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

  validateBug(title: string, description: string, version: string, fixedVersion: string, targetDate: Date, severity: string, username: string, username2: string,status: string) {
    let body = new URLSearchParams();
    body.set('title',title);
    body.set('description',description);
    body.set('version',version);
    body.set('fixedVersion',fixedVersion);
    body.set('targetDateString',targetDate.toISOString().slice(0,10));
    body.set('severityString',severity);
    body.set('assignedToString',username);
    body.set('createdByString',username2);
    body.set('statusString',status);

    console.log("adding here");
    return this.http.post<UserData>(this.baseURL + '/add-bug',
      body.toString(),
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/x-www-form-urlencoded'}
        )
      });
  }


  sort(title, version): Observable<BugData[]> {
    let params = new HttpParams();

    if (title) {
      params = params.append('title', title);
    }
    if (version) {
      params = params.append('version', version);
    }


    return this.http.get<BugData[]>(this.baseURL + '/listBugs/sort', {params: params});
  }

}
