package com.liyu.breeze.service.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gleiyu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiProjectParam extends PaginationParam {
    private String projectCode;

    private String projectName;
}
