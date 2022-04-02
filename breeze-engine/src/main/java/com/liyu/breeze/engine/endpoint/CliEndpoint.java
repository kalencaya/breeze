package com.liyu.breeze.engine.endpoint;

import com.liyu.breeze.common.enums.DeploymentTarget;
import org.apache.flink.configuration.Configuration;

public interface CliEndpoint {

    void submitApplication(DeploymentTarget deploymentTarget, Configuration configuration, PackageJarJob job) throws Exception;

    void submit(DeploymentTarget deploymentTarget, Configuration configuration, PackageJarJob job) throws Exception;

}


