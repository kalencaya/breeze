import { Dict, QueryParam } from './app.data';

export class DictType {
  id?: number;
  dictTypeCode: string;
  dictTypeName: string;
  remark?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class DictTypeParam extends QueryParam {
  dictTypeCode?: string;
  dictTypeName?: string;
}

export class DictData {
  id?: number;
  dictType?: DictType;
  dictCode?: string;
  dictValue?: string;
  remark?: string;
  isValid?: string;
  createTime?: Date;
  updateTime?: Date;
}

export class DictDataParam extends QueryParam {
  dictTypeCode?: string;
  dictCode?: string;
  dictValue?: string;
  isValid?: string;
}

export class Role {
  id?: number;
  roleCode: string;
  roleName: string;
  roleType?: Dict;
  roleStatus: Dict;
  roleDesc?: string;
  showOpIcon?: boolean;
}

export class Dept {
  id?: number;
  deptCode?: string;
  deptName: string;
  pid?: string;
  deptStatus?: Dict;
}
