package com.liyu.breeze.engine.endpoint;

import com.liyu.breeze.common.enums.DeploymentTarget;
import com.liyu.breeze.engine.endpoint.impl.CliEndpointImpl;
import org.apache.flink.client.deployment.executors.RemoteExecutor;
import org.apache.flink.configuration.*;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * 1.添加 mysql-connector-java.jar 依赖到项目中
 * 2.copy seatunnel-core-flink.jar 到 FLINK_HOME/lib 目录下（Standalone 模式）。
 * 3.运行单元测试
 */
class CliEndpointTest {

    private String seatunnelHome = "/Users/wangqi/Downloads/apache-seatunnel-incubating-2.0.5-SNAPSHOT";
    private String seatunnelPath = seatunnelHome + "/lib/seatunnel-core-flink.jar";

    @Test
    void testStandaloneSubmit() throws Exception {
        CliEndpoint endpoint = new CliEndpointImpl();
        endpoint.submit(DeploymentTarget.STANDALONE_SESSION, buildConfiguration(), buildJarJob());
    }

    private Configuration buildConfiguration() throws MalformedURLException {
        Configuration configuration = new Configuration();
        configuration.setString(JobManagerOptions.ADDRESS, "localhost");
        configuration.setInteger(JobManagerOptions.PORT, 6123);
        List<URL> jars = Arrays.asList(new URL("file://" + seatunnelPath));
        ConfigUtils.encodeCollectionToConfig(configuration, PipelineOptions.JARS, jars, Object::toString);
        configuration.setString(DeploymentOptions.TARGET, RemoteExecutor.NAME);
        return configuration;
    }

    private PackageJarJob buildJarJob() throws IOException {
        PackageJarJob job = new PackageJarJob();
        job.setJarFilePath(seatunnelPath);
        job.setEntryPointClass("org.apache.seatunnel.SeatunnelFlink");
        URL resource = getClass().getClassLoader().getResource("flink_jdbc_file.conf");
        job.setProgramArgs(new String[]{"--config", resource.getPath()});
        job.setClasspaths(Arrays.asList());
        job.setSavepointSettings(SavepointRestoreSettings.none());
        return job;
    }
}
