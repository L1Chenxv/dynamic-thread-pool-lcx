package cn.lcx.middleware.dynamic.thread.pool.types;

import cn.lcx.middleware.dynamic.thread.pool.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.PrimitiveIterator;

/**
 * @description:
 * @author: L1Chenxv
 * @create: 2024-11-10 17:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 342413762455226400L;

    private String code;

    private T data;

    private String msg;


    public static final <T> Response ofSuccess(T data) {
        return Response.builder()
            .code(Code.SUCCESS.getCode())
            .msg(Code.SUCCESS.getMsg())
            .data(data)
            .build();
    }

    public static final <T> Response ofError(T data) {
        return Response.builder()
            .code(Code.UN_ERROR.getCode())
            .msg(Code.UN_ERROR.getMsg())
            .data(data)
            .build();
    }

}
