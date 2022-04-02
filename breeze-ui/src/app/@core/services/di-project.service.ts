import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse, ResponseBody } from '../data/app.data';
import { DiProject } from '../data/studio.data';

@Injectable({
  providedIn: 'root',
})
export class DiProjectService {
  private url = 'api/di/project';
  constructor(private http: HttpClient) {}

  listByPage(queryParam): Observable<PageResponse<DiProject>> {
    const params: HttpParams = new HttpParams({ fromObject: queryParam });
    return this.http.get<PageResponse<DiProject>>(`${this.url}`, { params });
  }

  delete(row: DiProject): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + row.id;
    return this.http.delete<ResponseBody<any>>(delUrl);
  }

  deleteBatch(rows: DiProject[]): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + 'batch';
    let params = rows.map((row) => row.id);
    return this.http.post<ResponseBody<any>>(delUrl, { ...params });
  }

  add(row: DiProject): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(this.url, row);
  }

  update(row: DiProject): Observable<ResponseBody<any>> {
    return this.http.put<ResponseBody<any>>(this.url, row);
  }
}
