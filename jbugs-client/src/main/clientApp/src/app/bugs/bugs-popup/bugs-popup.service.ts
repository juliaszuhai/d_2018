import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "../../../../node_modules/@angular/common/http";
import {saveAs} from 'file-saver/FileSaver';
import {BugData} from "../bugs.service";

@Injectable({
  providedIn: 'root'
})
export class BugsPopupService {

  constructor(private http: HttpClient) {
  }

  baseURL = 'http://localhost:8080/jbugs/rest';

  getPdf(id: number) {

    const params = new HttpParams().set('id', id.toString());
    const headers = new HttpHeaders();
    headers.append('accept', 'application/pdf');
    return this.http.get(this.baseURL + '/pdfbug', {params: params, responseType: 'blob'})
      .subscribe(res => {
        let filename = 'BugPdf.pdf';
        saveAs(res, filename);
      });

  }

  getFile(id: number, data: BugData) {
    const params = new HttpParams().set('bugId', id.toString());
    const headers = new HttpHeaders();
    headers.append('accept', 'application/octet-stream');
    return this.http.get(this.baseURL + '/list-bugs/get-attachment', {params: params, responseType: 'blob'})
      .subscribe(res => {
        let filename = data.fileName;
        saveAs(res, filename);
      });
  }
}
