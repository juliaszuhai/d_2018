import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import * as moment from "moment";
import _date = moment.unitOfTime._date;
import {UserData} from "../authentication/authentication.service";



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

  getBugsByTitle(title: string):  Observable<BugData[]> {

    let params = new HttpParams();
    params.set('title', title);


    return this.http.get<BugData[]>(this.baseURL + '/listBugsByTitle/' + title,  {params}

    );
  }






}
