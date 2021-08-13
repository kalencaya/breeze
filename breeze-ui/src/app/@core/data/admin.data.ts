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
  isValid?: Dict;
  createTime?: Date;
  updateTime?: Date;
}

export class DictDataParam extends QueryParam {
  dictTypeCode?: string;
  dictCode?: string;
  dictValue?: string;
  isValid?: Dict;
}
