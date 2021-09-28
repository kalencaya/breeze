import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-user-center',
  templateUrl: './user-center.component.html',
  styleUrls: ['./user-center.component.scss'],
})
export class UserCenterComponent implements OnInit {
  parent: HTMLElement;
  @Input() data: any;
  menus = [
    {
      isActive: true,
      title: '个人资料',
    },
    {
      isActive: false,
      title: '安全设置',
    },
    {
      isActive: false,
      title: '消息通知',
    },
    {
      isActive: false,
      title: '登录日志',
    },
  ];
  constructor(private elr: ElementRef, private translate: TranslateService) {}

  ngOnInit(): void {
    this.parent = this.elr.nativeElement.parentElement;
  }
  itemClickFn(clickedItem) {
    this.menus.forEach((item) => {
      item.isActive = false;
    });
    clickedItem.isActive = true;
  }
}
