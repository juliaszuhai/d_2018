import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import {UserData} from "../authentication/authentication.service";
import {forEach} from "@angular/router/src/utils/collection";



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

  getLoggedUserName():string{
    return localStorage.getItem("id_token");
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

  getBugsByStatus(status: string):  Observable<BugData[]> {

    let params = new HttpParams();
    params.set('status', status.toUpperCase());


    return this.http.get<BugData[]>(this.baseURL + '/listBugsByStatus/' + status.toUpperCase(),  {params}

    );
  }

  getBugsBySeverity(severity: string):  Observable<BugData[]> {

    let params = new HttpParams();
    params.set('severity', severity.toUpperCase());


    return this.http.get<BugData[]>(this.baseURL + '/listBugsBySeverity/' + severity.toUpperCase(),  {params}

    );
  }

  getBugsByDescription(description: string):  Observable<BugData[]> {

    let params = new HttpParams();
    params.set('description', description);


    return this.http.get<BugData[]>(this.baseURL + '/listBugsByDescription/' + description,  {params}

    );
  }


}
