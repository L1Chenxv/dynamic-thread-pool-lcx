package cn.lcx.middleware.dynamic.thread.pool.enums;

public enum Code {

    SUCCESS("2000", "调用成功"),
    UN_ERROR("5001", "调用失败"),
    ILLEGAL_PARAMETER("5002", "非法参数"),
    ;

    private String code;
    private String msg;

    Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
