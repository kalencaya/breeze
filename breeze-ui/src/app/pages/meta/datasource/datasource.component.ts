import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DataTableComponent, DialogService, LoadingService, ModalService } from 'ng-devui';
import { DEFAULT_PAGE_PARAM, Dict, DICT_TYPE, PRIVILEGE_CODE } from 'src/app/@core/data/app.data';
import { DataSourceMeta, DataSourceMetaParam } from 'src/app/@core/data/meta.data';
import { AuthService } from 'src/app/@core/services/auth.service';
import { DataSourceService } from 'src/app/@core/services/datasource.service';
import { DictDataService } from 'src/app/@core/services/dict-data.service';
import { DatasourceDeleteComponent } from './datasource-delete/datasource-delete.component';
import { DatasourceNewPreComponent } from './datasource-new-pre/datasource-new-pre.component';
import { DatasourceUpdateComponent } from './datasource-update/datasource-update.component';

@Component({
  selector: 'app-datasource',
  templateUrl: './datasource.component.html',
  styleUrls: ['./datasource.component.scss'],
})
export class DatasourceComponent implements OnInit {
  PRIVILEGE_CODE = PRIVILEGE_CODE;
  @ViewChild('dataTable', { static: true }) dataTable: DataTableComponent;
  dataLoading: boolean = false;
  dataTableChecked: boolean = false;
  loadTarget: any;
  dataTableDs: DataSourceMeta[] = [];
  pager = {
    total: 0,
    pageIndex: DEFAULT_PAGE_PARAM.pageIndex,
    pageSize: DEFAULT_PAGE_PARAM.pageSize,
    pageSizeOptions: DEFAULT_PAGE_PARAM.pageParams,
  };
  searchFormConfig = { dataSourceName: '', dataSourceType: null, hostName: '', databaseName: '' };
  dataSourceTypeList: Dict[] = [];
  constructor(
    public authService: AuthService,
    @Inject(DOCUMENT) private doc: any,
    private loadingService: LoadingService,
    private translate: TranslateService,
    private modalService: ModalService,
    private dataSourceService: DataSourceService,
    private dictDataService: DictDataService,
    private dialogService: DialogService
  ) {}

  ngOnInit(): void {
    this.refreshTable();
    this.dictDataService.listByType(DICT_TYPE.datasourceType).subscribe((d) => {
      this.dataSourceTypeList = d;
    });
  }

  refreshTable() {
    this.openDataTableLoading();
    let param: DataSourceMetaParam = {
      pageSize: this.pager.pageSize,
      current: this.pager.pageIndex,
      dataSourceName: this.searchFormConfig.dataSourceName,
      dataSourceType: this.searchFormConfig.dataSourceType ? this.searchFormConfig.dataSourceType.value : '',
      hostName: this.searchFormConfig.hostName,
      databaseName: this.searchFormConfig.databaseName,
    };
    this.dataSourceService.listByPage(param).subscribe((d) => {
      this.pager.total = d.total;
      this.dataTableDs = d.records;
      this.loadTarget.loadingInstance.close();
      this.dataLoading = false;
      this.dataTable.setTableCheckStatus({ pageAllChecked: false });
      this.getDataTableCheckedStatus();
    });
  }

  openDataTableLoading() {
    const dc = this.doc.querySelector('#dataTableContent');
    this.loadTarget = this.loadingService.open({
      target: dc,
      message: this.translate.instant('app.common.loading'),
      positionType: 'relative',
      zIndex: 1,
    });
    this.dataLoading = true;
  }

  reset() {
    this.searchFormConfig = { dataSourceName: '', dataSourceType: '', hostName: '', databaseName: '' };
    this.pager = {
      total: 0,
      pageIndex: DEFAULT_PAGE_PARAM.pageIndex,
      pageSize: DEFAULT_PAGE_PARAM.pageSize,
      pageSizeOptions: DEFAULT_PAGE_PARAM.pageParams,
    };
    this.refreshTable();
  }

  openAddDataSourceDialog() {
    const results = this.modalService.open({
      id: 'datasource-new-pre',
      width: '480px',
      backdropCloseable: true,
      placement: 'top',
      component: DatasourceNewPreComponent,
      data: {
        title: '',
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshTable();
        },
      },
    });
  }

  openDeleteDataSourceDialog(items: DataSourceMeta[]) {
    const results = this.modalService.open({
      id: 'datasource-delete',
      width: '346px',
      backdropCloseable: true,
      component: DatasourceDeleteComponent,
      data: {
        title: this.translate.instant('app.common.operate.delete.confirm.title'),
        items: items,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshTable();
        },
      },
    });
  }

  openEditDataSourceDialog(item: DataSourceMeta) {
    const results = this.modalService.open({
      id: 'datasource-edit',
      width: '580px',
      backdropCloseable: true,
      component: DatasourceUpdateComponent,
      data: {
        title: { name: this.translate.instant('meta.datasource') },
        item: item,
        onClose: (event: any) => {
          results.modalInstance.hide();
        },
        refresh: () => {
          this.refreshTable();
        },
      },
    });
  }

  openShowPasswordDialog(item: DataSourceMeta) {
    this.dataSourceService.showPassword(item).subscribe((d) => {
      if (d.success) {
        const results = this.dialogService.open({
          id: 'datasource-show-password',
          width: '350px',
          maxHeight: '600px',
          title: this.translate.instant('meta.password'),
          content: d.data,
          backdropCloseable: true,
          dialogtype: 'info',
          buttons: [],
        });
      }
    });
  }

  getDataTableCheckedStatus() {
    if (this.dataTable.getCheckedRows().length > 0) {
      this.dataTableChecked = true;
    } else {
      this.dataTableChecked = false;
    }
  }
}
