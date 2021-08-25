import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ITreeItem, LoadingService, ModalService, OperableTreeComponent, TreeNode } from 'ng-devui';
import { Role } from 'src/app/@core/data/admin.data';
import { DeptService } from 'src/app/@core/services/dept.service';
import { RoleService } from 'src/app/@core/services/role.service';
import { DeptDeleteComponent } from '../dept/dept-delete/dept-delete.component';
import { DeptNewComponent } from '../dept/dept-new/dept-new.component';
import { DeptUpdateComponent } from '../dept/dept-update/dept-update.component';
import { RoleDeleteComponent } from '../role/role-delete/role-delete.component';
import { RoleNewComponent } from '../role/role-new/role-new.component';
import { RoleUpdateComponent } from '../role/role-update/role-update.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  @ViewChild('operableTree', { static: false }) operableTree: OperableTreeComponent;
  roleList: Role[] = [];
  roleTab: string = 'role';
  deptTab: string = 'dept';
  tabId: string = this.roleTab;
  roleBtnStyle: string;
  deptBtnStyle: string;
  deptList: ITreeItem[];
  searchTip;

  constructor(
    private roleService: RoleService,
    @Inject(DOCUMENT) private doc: any,
    private loadingService: LoadingService,
    private translate: TranslateService,
    private modalService: ModalService,
    private deptService: DeptService
  ) {}

  ngOnInit(): void {
    this.searchTip = this.translate.instant('app.common.operate.query.tip');
    this.refreshRoleList();
    this.refreshDeptList();
  }

  refreshRoleList() {
    this.roleService.listAll().subscribe((d) => {
      this.roleList = d;
    });
    this.switchBtnStyle();
  }

  refreshDeptList() {
    this.deptService.listAll().subscribe((d) => {
      this.deptList = d;
    });
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
      this.openAddDeptDialog(null, null);
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

  openAddDeptDialog(event, node) {
    const results = this.modalService.open({
      id: 'dept-new',
      width: '580px',
      backdropCloseable: true,
      component: DeptNewComponent,
      data: {
        title: { name: this.translate.instant('admin.dept') },
        item: node,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: (node: TreeNode) => {
          this.operableTree.treeFactory.addNode(node);
          if (node.parentId != undefined) {
            this.operableTree.treeFactory.openNodesById(node.id);
            this.operableTree.treeFactory.activeNodeById(node.id);
          }
        },
      },
    });
  }

  openEditDeptDialog(event, node) {
    const results = this.modalService.open({
      id: 'dept-edit',
      width: '580px',
      backdropCloseable: true,
      component: DeptUpdateComponent,
      data: {
        title: { name: this.translate.instant('admin.dept') },
        item: node,
        index: this.operableTree.treeFactory.getNodeIndex(node),
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: (node) => {
          const children: TreeNode[] = this.operableTree.treeFactory.getChildrenById(node.id);
          this.operableTree.treeFactory.deleteNodeById(node.id);
          if (children.length > 0) {
            this.operableTree.treeFactory.addNode(
              {
                id: node.id,
                parentId: node.parentId,
                title: node.data.title,
              },
              node.index
            );
            this.operableTree.treeFactory.startLoading(node.id);
            this.deptService.listChilds(node.id).subscribe((d) => {
              this.operableTree.treeFactory.endLoading(node.id);
              this.operableTree.treeFactory.mapTreeItems({
                treeItems: d,
                parentId: node.id,
                treeNodeIdKey: 'deptId',
                treeNodeTitleKey: 'deptName',
                treeNodeChildrenKey: 'children',
              });
            });
          } else {
            this.operableTree.treeFactory.addNode({ id: node.id, parentId: node.parentId, title: node.data.title }, node.index);
          }
        },
      },
    });
  }

  loadChildren(node: TreeNode) {
    if (this.operableTree.treeFactory.getChildrenById(node.id).length === 0 && node.data.isParent) {
      if (!this.operableTree.treeFactory.nodes[node.id].data.loading) {
        this.operableTree.treeFactory.startLoading(node.id);
        this.deptService.listChilds(node.id).subscribe((d) => {
          this.operableTree.treeFactory.endLoading(node.id);
          this.operableTree.treeFactory.mapTreeItems({ treeItems: d, parentId: node.id });
        });
      }
    }
  }

  openDeleteDeptDialog(event, node) {
    const results = this.modalService.open({
      id: 'dept-delete',
      width: '346px',
      backdropCloseable: true,
      component: DeptDeleteComponent,
      data: {
        title: this.translate.instant('app.common.operate.delete.confirm.title'),
        items: node,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.operableTree.treeFactory.deleteNodeById(node.id);
        },
      },
    });
  }

  beforeNodeDrop = (dragNodeId, dropNodeId, dropType) => {
    let dragNode = this.operableTree.treeFactory.getNodeById(dragNodeId);
    let dropNode = this.operableTree.treeFactory.getNodeById(dropNodeId);
    this.deptService
      .update({ id: dragNode.originItem.deptId, pid: dropType == 'inner' ? dropNodeId : dropNode.originItem.pid, deptName: dragNode.title })
      .subscribe((d) => {});
    return new Promise((resovle) => {
      resovle(undefined);
    });
  };

  searchDeptTree(event) {
    this.operableTree.operableTree.treeFactory.searchTree(event, true);
  }
}
