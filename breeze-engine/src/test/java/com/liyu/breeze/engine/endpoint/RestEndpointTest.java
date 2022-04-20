package com.liyu.breeze.engine.endpoint;

import com.liyu.breeze.engine.endpoint.impl.RestEndpointImpl2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.runtime.rest.handler.async.TriggerResponse;
import org.apache.flink.runtime.rest.messages.EmptyResponseBody;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerRequestBody;
import org.apache.flink.runtime.rest.messages.job.savepoints.stop.StopWithSavepointRequestBody;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * cancel 有 2 种方式：是否创建 savepoint。
 * stop 等同于带有 savepoint 的 cancel。
 * cancel 任务状态为 canceled，而 stop 任务状态为 finished
 */
class RestEndpointTest {

    @Test
    void testStop() throws Exception {
        Configuration configuration = GlobalConfiguration.loadConfiguration();
        configuration.setString(RestOptions.ADDRESS, "localhost");
        configuration.setInteger(RestOptions.PORT, 8081);
        RestEndpoint endpoint = new RestEndpointImpl2(configuration);
        StopWithSavepointRequestBody requestBody = new StopWithSavepointRequestBody("/Users/wangqi/Downloads/savepoint", true);
        final CompletableFuture<TriggerResponse> future = endpoint.jobStop("39b6a5988f1a2427ead8d86cb05e46c4", requestBody);
        // fixme 很奇怪，必须调用一下才能取消任务
        future.get();
    }

    @Test
    void testCancelWithSavepoint() throws Exception {
        Configuration configuration = GlobalConfiguration.loadConfiguration();
        configuration.setString(RestOptions.ADDRESS, "localhost");
        configuration.setInteger(RestOptions.PORT, 8081);
        RestEndpoint endpoint = new RestEndpointImpl2(configuration);
        SavepointTriggerRequestBody requestBody = new SavepointTriggerRequestBody("/Users/wangqi/Downloads/savepoint", true);
        endpoint.jobSavepoint("2c57f1f010c464625ad953e25bd945f5", requestBody);
    }

    @Test
    void testCancelWithoutSavepoint() throws Exception {
        Configuration configuration = GlobalConfiguration.loadConfiguration();
        configuration.setString(RestOptions.ADDRESS, "localhost");
        configuration.setInteger(RestOptions.PORT, 8081);
        RestEndpoint endpoint = new RestEndpointImpl2(configuration);
        final CompletableFuture<EmptyResponseBody> future = endpoint.jobTerminate("feb244f64317aee7209c1534da87005b", "cancel");
        // fixme 很奇怪，必须调用一下才能取消任务
        future.get();
    }
}
