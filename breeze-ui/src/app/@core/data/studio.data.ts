import { Dict, QueryParam } from './app.data';

export const WORKBENCH_MENU = [
  {
    title: '输入',
    menuIcon: 'icon icon-folder',
    children: [
      { title: 'CSV文件输入', menuIcon: 'icon-file', menuType: 'source', menuName: 'source-csv' },
      { title: 'EXCEL文件输入', menuIcon: 'icon-file', menuType: 'source', menuName: 'source-excel' },
      { title: '表输入', menuIcon: 'icon-table', menuType: 'source', menuName: 'source-table' },
    ],
  },
  {
    title: '输出',
    menuIcon: 'icon icon-folder',
    children: [
      { title: 'CSV文件输出', menuIcon: 'icon-file', menuType: 'sink', menuName: 'sink-csv' },
      { title: 'EXCEL文件输出', menuIcon: 'icon-file', menuType: 'sink', menuName: 'sink-csv' },
      { title: '表输出', menuIcon: 'icon-table', menuType: 'sink', menuName: 'sink-csv' },
    ],
  },
  {
    title: '转换',
    menuIcon: 'icon icon-folder',
    children: [
      { title: '字段选择', menuIcon: 'icon-property', menuType: 'trans', menuName: 'trans-field-select' },
      { title: '设置字段值', menuIcon: 'icon-set-keyword', menuType: 'trans', menuName: 'trans-set-field-value' },
      { title: '数据聚合', menuIcon: 'icon-groupby', menuType: 'trans', menuName: 'trans-group' },
    ],
  },
  {
    title: '流程',
    menuIcon: 'icon icon-folder',
    children: [
      { title: '数据过滤', menuIcon: 'icon-filter-o', menuType: 'trans', menuName: 'trans-filter' },
      { title: 'SWITCH / CASE', menuIcon: 'icon-switch', menuType: 'trans', menuName: 'trans-case' },
    ],
  },
];

export class DiProject {
  id?: number;
  projectCode?: string;
  projectName?: string;
  remark?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class DiProjectParam extends QueryParam {
  projectCode: string;
  projectName?: string;
}

export class DiDirectory {
  id?: number;
  projectId?: number;
  directoryName: string;
  pid?: number;
  fullPath?: string;
}

export class DiJob {
  id?: number;
  projectId: number;
  jobCode: string;
  jobName: string;
  directory: DiDirectory;
  jobType?: Dict;
  jobStatus?: Dict;
  runtimeState?: Dict;
  jobVersion?: number;
  remark?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class DiJobParam extends QueryParam {
  projectId: number;
  jobCode: string;
  jobName: string;
  jobType: string;
  runtimeState: string;
  directoryId?: string;
}
