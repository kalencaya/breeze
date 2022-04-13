import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse, ResponseBody } from '../data/app.data';
import { DiResourceFile } from '../data/studio.data';

@Injectable({
  providedIn: 'root',
})
export class DiResourceFileService {
  private url = 'api/di/resource';
  constructor(private http: HttpClient) {}

  listByPage(queryParam): Observable<PageResponse<DiResourceFile>> {
    const params: HttpParams = new HttpParams({ fromObject: queryParam });
    return this.http.get<PageResponse<DiResourceFile>>(`${this.url}`, { params });
  }

  delete(row: DiResourceFile): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + row.id;
    return this.http.delete<ResponseBody<any>>(delUrl);
  }

  deleteBatch(rows: DiResourceFile[]): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + 'batch';
    let params = rows.map((row) => row.id);
    return this.http.post<ResponseBody<any>>(delUrl, { ...params });
  }

  add(row: DiResourceFile): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(this.url, row);
  }

  update(row: DiResourceFile): Observable<ResponseBody<any>> {
    return this.http.put<ResponseBody<any>>(this.url, row);
  }
}
