import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ITreeItem } from 'ng-devui';
import { Observable } from 'rxjs';
import { Dept } from '../data/admin.data';
import { ResponseBody } from '../data/app.data';

@Injectable({
  providedIn: 'root',
})
export class DeptService {
  private url = 'api/admin/dept';
  constructor(private http: HttpClient) {}

  listAll(): Observable<ITreeItem[]> {
    return this.http.get<ITreeItem[]>(`${this.url}`);
  }

  listChilds(pid): Observable<ITreeItem[]> {
    return this.http.get<ITreeItem[]>(`${this.url}/` + pid);
  }

  add(row: Dept): Observable<ResponseBody<any>> {
    return this.http.post<ResponseBody<any>>(`${this.url}`, row);
  }

  update(row: Dept): Observable<ResponseBody<any>> {
    return this.http.put<ResponseBody<any>>(this.url, row);
  }

  delete(row: Dept): Observable<ResponseBody<any>> {
    const delUrl = `${this.url}/` + row.id;
    return this.http.delete<ResponseBody<any>>(delUrl);
  }
}
