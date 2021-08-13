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

/**登录参数 */
export interface LoginParams {
  userName: string;
  password: string;
  authCode: string;
  uuid: string;
  remember: boolean;
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
  code?: string;
  value?: string;
}

/** 适配前端的枚举结构*/
export interface DictVO {
  label?: string;
  value?: string;
}
/**分页参数 */
export const DEFAULT_PAGE_PARAM = {
  pageSize: 10,
  pageIndex: 0,
  pageParams: [10, 20, 50, 100],
};
