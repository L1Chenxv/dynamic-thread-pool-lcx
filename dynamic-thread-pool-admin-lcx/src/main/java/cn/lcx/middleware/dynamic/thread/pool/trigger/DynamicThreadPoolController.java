package cn.lcx.middleware.dynamic.thread.pool.trigger;

import cn.lcx.middleware.dynamic.thread.pool.convert.RequestConverter;
import cn.lcx.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.lcx.middleware.dynamic.thread.pool.sdk.domain.model.valobj.RegistryEnumVO;
import cn.lcx.middleware.dynamic.thread.pool.types.Request;
import cn.lcx.middleware.dynamic.thread.pool.types.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: L1Chenxv
 * @create: 2024-11-10 17:14
 */
@Slf4j
@RestController()
@RequestMapping("/api/v1/dynamic/thread/pool")
@CrossOrigin("*")
public class DynamicThreadPoolController {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 查询线程池数据
     * curl --request GET \
     * --url 'http://localhost:8089/api/v1/dynamic/thread/pool/query_thread_pool_list'
     */
    @GetMapping(value = "/query_thread_pool_list")
    public Response<List<ThreadPoolConfigEntity>> queryThreadPoolList() {
        try {
            RList<ThreadPoolConfigEntity> cacheList = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
            return Response.ofSuccess(cacheList.readAll());
        } catch (Exception e) {
            log.error("查询线程池数据异常", e);
            return Response.ofError(null);
        }

    }

    /**
     * 查询线程池配置
     * curl --request GET \
     * --url 'http://localhost:8089/api/v1/dynamic/thread/pool/query_thread_pool_config?appName=dynamic-thread-pool-test-app&threadPoolName=threadPoolExecutor'
     */
    @GetMapping(value = "/query_thread_pool_config")
    public Response<ThreadPoolConfigEntity> queryThreadPoolConfig(@RequestParam String appName, @RequestParam String threadPoolName) {
        try {
            String cacheKey = RegistryEnumVO.THREAD_POOL_CONFIG_PARAMETER_LIST_KEY + "_" + appName + "_" + threadPoolName;
            ThreadPoolConfigEntity threadPoolConfigEntity = redissonClient.<ThreadPoolConfigEntity>getBucket(cacheKey).get();
            return Response.<ThreadPoolConfigEntity>ofSuccess(threadPoolConfigEntity);
        } catch (Exception e) {
            log.error("查询线程池配置异常", e);
            return Response.ofError(null);
        }
    }

    /**
     * 修改线程池配置
     * curl --request POST \
     * --url http://localhost:8089/api/v1/dynamic/thread/pool/update_thread_pool_config \
     * --header 'content-type: application/json' \
     * --data '{
     * "appName":"dynamic-thread-pool-test-app",
     * "threadPoolName": "threadPoolExecutor",
     * "corePoolSize": 1,
     * "maximumPoolSize": 10
     * }'
     */
    @PostMapping(value = "/update_thread_pool_config")
    public Response<Boolean> updateThreadPoolConfig(@RequestBody Request request) {
        ThreadPoolConfigEntity threadPoolConfig = RequestConverter.INSTANCE.request2ConfigEntity(request);
        try {
            log.info("修改线程池配置开始 {} {} {}", threadPoolConfig.getAppName(), threadPoolConfig.getThreadPoolName(), JSON.toJSONString(request));
            RTopic topic = redissonClient.getTopic(RegistryEnumVO.DYNAMIC_THREAD_POOL_REDIS_TOPIC.getKey() + "_" + threadPoolConfig.getAppName());
            topic.publish(request);
            log.info("修改线程池配置完成 {} {}", threadPoolConfig.getAppName(), threadPoolConfig.getThreadPoolName());
            return Response.<Boolean>ofSuccess(true);
        } catch (Exception e) {
            log.error("修改线程池配置异常 {}", JSON.toJSONString(request), e);
            return Response.<Boolean>ofError(false);
        }
    }

}
