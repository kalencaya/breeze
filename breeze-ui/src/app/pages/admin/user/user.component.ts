import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { LoadingService, ModalService } from 'ng-devui';
import { Role } from 'src/app/@core/data/admin.data';
import { RoleService } from 'src/app/@core/services/role.service';
import { RoleDeleteComponent } from '../role/role-delete/role-delete.component';
import { RoleNewComponent } from '../role/role-new/role-new.component';
import { RoleUpdateComponent } from '../role/role-update/role-update.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  roleList: Role[] = [];
  roleTab: string = 'role';
  deptTab: string = 'dept';
  tabId: string = this.roleTab;
  roleBtnStyle: string;
  deptBtnStyle: string;

  constructor(
    private roleService: RoleService,
    @Inject(DOCUMENT) private doc: any,
    private loadingService: LoadingService,
    private translate: TranslateService,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.refreshRoleList();
  }

  refreshRoleList() {
    this.roleService.listAll().subscribe((d) => {
      console.log(d);
      console.log(this.roleList);
      this.roleList = d;
    });
    this.switchBtnStyle();
  }

  refreshDeptList() {
    this.switchBtnStyle();
  }

  switchBtnStyle() {
    if (this.tabId === this.deptTab) {
      this.roleBtnStyle = 'common';
      this.deptBtnStyle = 'primary';
    } else if (this.tabId === this.roleTab) {
      this.roleBtnStyle = 'primary';
      this.deptBtnStyle = 'common';
    }
  }
  showDept() {
    this.tabId = this.deptTab;
    this.refreshDeptList();
  }
  showRole() {
    this.tabId = this.roleTab;
    this.refreshRoleList();
  }
  mover(node: Role) {
    node.showOpIcon = true;
  }

  mleave(node: Role) {
    node.showOpIcon = false;
  }

  openAddDialog() {
    if (this.tabId === this.roleTab) {
      this.openAddRoleDialog();
    } else if (this.tabId === this.deptTab) {
      this.openAddDeptDialog();
    }
  }
  openAddRoleDialog() {
    const results = this.modalService.open({
      id: 'role-new',
      width: '580px',
      backdropCloseable: true,
      component: RoleNewComponent,
      data: {
        title: { name: this.translate.instant('admin.role') },
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshRoleList();
        },
      },
    });
  }

  openAddDeptDialog() {
    console.log('open dept dialog');
  }

  openEditRoleDialog(item: Role) {
    const results = this.modalService.open({
      id: 'role-edit',
      width: '580px',
      backdropCloseable: true,
      component: RoleUpdateComponent,
      data: {
        title: { name: this.translate.instant('admin.role') },
        item: item,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshRoleList();
        },
      },
    });
  }

  openDeleteRoleDialog(items: Role) {
    const results = this.modalService.open({
      id: 'role-delete',
      width: '346px',
      backdropCloseable: true,
      component: RoleDeleteComponent,
      data: {
        title: this.translate.instant('app.common.operate.delete.confirm.title'),
        items: items,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshRoleList();
        },
      },
    });
  }
}
