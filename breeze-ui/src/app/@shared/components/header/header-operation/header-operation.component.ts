import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/@core/services/auth.service';
import { LANGUAGES } from 'src/config/language-config';
import { I18nService } from 'ng-devui/i18n';
import { OnlineUserInfo, USER_AUTH } from 'src/app/@core/data/app.data';
import { UserService } from 'src/app/@core/services/user.service';

@Component({
  selector: 'da-header-operation',
  templateUrl: './header-operation.component.html',
  styleUrls: ['./header-operation.component.scss'],
})
export class HeaderOperationComponent implements OnInit {
  user: OnlineUserInfo;
  languages = LANGUAGES;
  language;
  haveLoggedIn = false;

  constructor(
    private route: Router,
    private authService: AuthService,
    private translate: TranslateService,
    private i18n: I18nService,
    private userService: UserService
  ) {
    //通过token获取用户权限角色信息
    let token: string = localStorage.getItem(USER_AUTH.token);
    if (token != null && token != undefined && token != '') {
      this.userService.getOnlineUserInfo(token).subscribe((d) => {
        if (d.success) {
          this.user = d.data;
          this.authService.setSession(this.user);
          this.haveLoggedIn = true;
        }
      });
    }
  }

  ngOnInit() {
    this.language = this.translate.currentLang;
  }

  onSearch(event) {
    console.log(event);
  }

  onLanguageClick(language) {
    this.language = language;
    localStorage.setItem('lang', this.language);
    this.i18n.toggleLang(this.language);
    this.translate.use(this.language);
  }

  handleUserOps(operation) {
    switch (operation) {
      case 'logout': {
        this.haveLoggedIn = false;
        this.authService.logout();
        this.route.navigate(['/', 'login']);
        break;
      }
      default:
        break;
    }
  }
}
