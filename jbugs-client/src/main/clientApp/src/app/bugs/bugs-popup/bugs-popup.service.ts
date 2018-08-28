import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "../../../../node_modules/@angular/common/http";
import {saveAs} from 'file-saver/FileSaver';

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

}
