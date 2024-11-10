package cn.lcx.middleware.dynamic.thread.pool.convert;

import cn.lcx.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.lcx.middleware.dynamic.thread.pool.types.Request;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @description: Request转换器
 * @author: L1Chenxv
 * @create: 2024-11-10 17:53
 */

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RequestConverter {

    RequestConverter INSTANCE = Mappers.getMapper(RequestConverter.class);

    ThreadPoolConfigEntity request2ConfigEntity(Request request);
}
