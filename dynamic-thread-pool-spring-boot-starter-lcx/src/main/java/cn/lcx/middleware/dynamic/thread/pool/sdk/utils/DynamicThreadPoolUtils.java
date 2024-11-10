package cn.lcx.middleware.dynamic.thread.pool.sdk.utils;

import cn.lcx.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 动态线程池工具类
 * @author: L1Chenxv
 * @create: 2024-11-10 15:28
 */
public class DynamicThreadPoolUtils {

    /**
     * 按照配置更新线程池
     * @param threadPoolExecutor 线程池
     * @param threadPoolConfigEntity 配置项
     */
    public static final void updateDynamicThreadPoolConfig(ThreadPoolExecutor threadPoolExecutor, ThreadPoolConfigEntity threadPoolConfigEntity) {
        threadPoolExecutor.setCorePoolSize(threadPoolConfigEntity.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize());
    }
}
