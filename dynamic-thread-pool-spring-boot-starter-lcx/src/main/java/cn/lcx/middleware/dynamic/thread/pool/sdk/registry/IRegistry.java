package cn.lcx.middleware.dynamic.thread.pool.sdk.registry;


import cn.lcx.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import java.util.List;

/**
 * @description: 注册中心接口
 * @author: L1Chenxv
 * @create: 2024-11-03 22:12
 */
public interface IRegistry {

    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolEntities);

    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);

}
