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

export class User {
  id?: number;
  userName: string;
  nickName?: string;
  email?: string;
  password?: string;
  realName?: string;
  idCardType?: Dict;
  idCardNo?: string;
  gender?: Dict;
  nation?: string;
  birthday?: Date;
  qq?: string;
  wechat?: string;
  mobilePhone?: string;
  userStatus?: Dict;
  summary?: string;
  registerChannel?: Dict;
  registerTime?: Date;
  registerIp?: string;
  roleCode?: string;
  deptCode?: string;
}

export class UserParam extends QueryParam {
  userName?: string;
  nickName?: string;
  email?: string;
  userStatus?: string;
  roleId?: string;
  deptId?: string;
}
