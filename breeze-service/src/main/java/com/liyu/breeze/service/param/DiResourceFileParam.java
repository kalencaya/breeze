package com.liyu.breeze.service.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gleiyu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiResourceFileParam extends PaginationParam {
    private Long projectId;
    private String fileName;
}
