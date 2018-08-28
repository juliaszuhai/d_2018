import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {saveAs} from 'file-saver/FileSaver';
import {TranslateService} from "@ngx-translate/core";


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

  constructor(private http: HttpClient,
              private translate: TranslateService) {
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

  validateBug(bug) {
    bug.targetDateString = bug.targetDate.toISOString().slice(0,10);
    bug.assignedToString=bug.assignedTo.username;
    bug.createdByUserString=bug.createdByUser.username;
    return this.http.post(this.baseURL + '/add-bug',
      bug,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
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

  excel(ids:number[]) {
    let httpParams = new HttpParams();
    ids.forEach(value => httpParams = httpParams.append("titles", value.toString()));
    const headers = new HttpHeaders();
    headers.append('accept', 'application/vnd.ms-excel');
    return this.http.get(this.baseURL+'/view-bugs', {params: httpParams, responseType: 'blob'})
      .subscribe(res=> {
          let filename = 'Export.xlsx';
          saveAs(res, filename);
      });

  }


  updateBug(bugData: BugData) {
    return this.http.post(this.baseURL + '/listBugs/updateBug', bugData,
      {
        headers: new HttpHeaders(
          {'Content-Type': 'application/json'}
        )
      })
  }


}
