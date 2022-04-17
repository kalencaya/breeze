package com.liyu.breeze.service.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gleiyu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiClusterConfigParam extends PaginationParam {

    private String clusterName;

    private String clusterType;
}
