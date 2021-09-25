/**验证码 */
export class AuthCode {
  uuid: string;
  img: string;
}
/**通用response结构 */
export interface ResponseBody<T> {
  success: boolean;
  data?: T;
  errorCode?: string;
  errorMessage: string;
  showType?: string;
}
export class PageResponse<T> {
  size: number;
  current: number;
  total: number;
  records: T[];
}

export class TransferData {
  name: string;
  value: string;
}

export class RegisterInfo {
  userName: string;
  email: string;
  password: string;
  confirmPassword: string;
  authCode: string;
  uuid: string;
}

/**登录参数 */
export interface LoginInfo {
  userName: string;
  password: string;
  authCode: string;
  uuid: string;
  remember: boolean;
}

export interface OnlineUserInfo {
  userName: string;
  email: string;
  token: string;
  privileges: string[];
  roles: string[];
  expireTime: bigint;
}

/**分页参数 */
export class QueryParam {
  pageSize?: number;
  current?: number;
  filter?: { [key: string]: any[] };
  sorter?: { [key: string]: any };
}
/**
 * 枚举的key和value
 */
export interface Dict {
  label?: string;
  value?: string;
}

/**分页参数 */
export const DEFAULT_PAGE_PARAM = {
  pageSize: 10,
  pageIndex: 0,
  pageParams: [10, 20, 50, 100],
};

export const DICT_TYPE = {
  roleStatus: 'role_status',
  userStatus: 'user_status',
  idCardType: 'id_card_type',
  gender: 'gender',
  nation: 'nation',
};

export const USER_AUTH = {
  token: 'u_token',
  userInfo: 'u_info',
  pCodes: 'u_pCode',
  roleSysAdmin: 'sys_super_admin',
};

export const PRIVILEGE_CODE = {
  adminShow: 'padm0',
  userShow: 'pusr0',
  privilegeShow: 'ppvg0',
  dictShow: 'pdic0',
  settingShow: 'pset0',
  dictTypeSelect: 'pdct4',
  dictTypeAdd: 'pdct1',
  dictTypeDelete: 'pdct3',
  dictTypeEdit: 'pdct2',
  dictDataSelect: 'pdcd4',
  dictDataAdd: 'pdcd1',
  dictDataDelete: 'pdcd3',
  dictDataEdit: 'pdcd2',
  userSelect: 'pusr4',
  userAdd: 'pusr1',
  userDelete: 'pusr3',
  userEdit: 'pusr2',
  roleSelect: 'prol4',
  roleAdd: 'prol1',
  roleDelete: 'prol3',
  roleEdit: 'prol2',
  roleGrant: 'prol5',
  deptSelect: 'pdep4',
  deptAdd: 'pdep1',
  deptDelete: 'pdep3',
  deptEdit: 'pdep2',
  deptGrant: 'pdep5',
};
