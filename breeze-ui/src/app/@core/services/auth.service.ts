import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, of, Observable } from 'rxjs';
import { User } from 'src/app/@shared/models/user';
import { AuthCode, LoginInfo, OnlineUserInfo, RegisterInfo, ResponseBody, USER_AUTH } from '../data/app.data';

const USERS = [
  {
    account: 'Admin',
    gender: 'male',
    userName: 'Admin',
    password: 'DevUI.admin',
    phoneNumber: '19999996666',
    email: 'admin@devui.com',
    userId: '100',
  },
  {
    account: 'User',
    gender: 'female',
    userName: 'User',
    password: 'DevUI.user',
    phoneNumber: '19900000000',
    email: 'user@devui.com',
    userId: '200',
  },
  {
    account: 'admin@devui.com',
    gender: 'male',
    userName: 'Admin',
    password: 'devuiadmin',
    phoneNumber: '19988888888',
    email: 'admin@devui.com',
    userId: '300',
  },
];

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  logout() {
    localStorage.removeItem(USER_AUTH.token);
    localStorage.removeItem(USER_AUTH.userInfo);
    localStorage.removeItem(USER_AUTH.pCodes);
  }

  setSession(userInfo: OnlineUserInfo) {
    localStorage.setItem(USER_AUTH.userInfo, JSON.stringify(userInfo));
    let pCodes: string[] = [];
    if (userInfo.roles != null && userInfo.roles != undefined) {
      userInfo.roles.forEach((d) => {
        pCodes.push(d);
      });
    }
    if (userInfo.privileges != null && userInfo.privileges != undefined) {
      userInfo.privileges.forEach((d) => {
        pCodes.push(d);
      });
    }
    localStorage.setItem(USER_AUTH.pCodes, JSON.stringify(pCodes));
  }

  isUserLoggedIn() {
    if (localStorage.getItem(USER_AUTH.token)) {
      return true;
    } else {
      return false;
    }
  }

  hasPrivilege(code: string): boolean {
    let pCodes: string[] = JSON.parse(localStorage.getItem(USER_AUTH.pCodes));
    if (pCodes != null && pCodes != undefined) {
      return pCodes.includes(USER_AUTH.roleSysAdmin) || pCodes.includes(code);
    } else {
      return false;
    }
  }

  refreshAuthImage(): Observable<AuthCode> {
    return this.http.get<AuthCode>('/api/authCode?d=' + new Date().getTime());
  }

  register(registerInfo: RegisterInfo) {
    return this.http.post<ResponseBody<any>>('/api/user/register', registerInfo);
  }

  login(loginInfo: LoginInfo) {
    return this.http.post<ResponseBody<any>>('/api/user/login', loginInfo);
  }
}
