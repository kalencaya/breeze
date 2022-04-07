package com.liyu.breeze.engine.endpoint;

import com.liyu.breeze.common.enums.DeploymentTarget;
import com.liyu.breeze.engine.endpoint.impl.CliEndpointImpl;
import org.apache.flink.client.deployment.executors.RemoteExecutor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.configuration.JobManagerOptions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

class CliEndpointTest {

    private String seatunnelHome = "/Users/wangqi/Downloads/apache-seatunnel-incubating-2.0.5-SNAPSHOT";

    private String seatunnelPath = seatunnelHome + "/lib/seatunnel-core-flink.jar";
    private ClassPathResource config = new ClassPathResource("classpath:flink_jdbc_file.conf");

    @Test
    private void testStandaloneSubmit() throws Exception {
        CliEndpoint endpoint = new CliEndpointImpl();
        endpoint.submit(DeploymentTarget.STANDALONE_SESSION, buildConfiguration(), buildJarJob());
    }

    private Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setString(JobManagerOptions.ADDRESS, "localhost");
        configuration.setInteger(JobManagerOptions.PORT, 6123);
        configuration.setString(DeploymentOptions.TARGET, RemoteExecutor.NAME);
        return configuration;
    }

    private PackageJarJob buildJarJob() throws IOException {
        PackageJarJob job = new PackageJarJob();
        job.setJarFilePath(seatunnelPath);
        job.setEntryPointClass("org.apache.seatunnel.SeatunnelFlink");
        job.setProgramArgs(new String[]{"--config", config.getFile().getAbsolutePath()});
        return job;
    }
}
