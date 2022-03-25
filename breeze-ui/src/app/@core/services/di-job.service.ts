import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse, ResponseBody } from '../data/app.data';
import { DiJob, DiJobAttr } from '../data/studio.data';

@Injectable({
  providedIn: 'root',
})
export class DiJobService {
  private url = 'api/di/job';
  constructor(private http: HttpClient) {}

  selectById(id: number): Observable<DiJob> {
    return this.http.get<DiJob>(`${this.url}/detail?id=` + id);
  }

  listByPage(queryParam): Observable<PageResponse<DiJob>> {
    const params: HttpParams = new HttpParams({ fromObject: queryParam });
    return this.http.get<PageResponse<DiJob>>(`${this.url}`, { params });
  }

  delete(row: DiJob): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + row.id;
    return this.http.delete<ResponseBody<any>>(delUrl);
  }

  deleteBatch(rows: DiJob[]): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + 'batch';
    let params = rows.map((row) => row.id);
    return this.http.post<ResponseBody<any>>(delUrl, { ...params });
  }

  add(row: DiJob): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(this.url, row);
  }

  update(row: DiJob): Observable<ResponseBody<any>> {
    return this.http.put<ResponseBody<any>>(this.url, row);
  }

  saveJobDetail(job: DiJob): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(`${this.url}/detail`, job);
  }

  listJobAttr(jobId: number): Observable<{ jobId: number; jobAttr: string; jobProp: string; engineProp: string }> {
    return this.http.get<{ jobId: number; jobAttr: string; jobProp: string; engineProp: string }>(`${this.url}/attr/` + jobId);
  }

  saveJobAttr(attrs: { jobId: number; jobAttr: string; jobProp: string; engineProp: string }): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(`${this.url}/attr`, attrs);
  }
}
