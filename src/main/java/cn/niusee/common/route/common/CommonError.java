package cn.niusee.common.route.common;

import cn.niusee.common.route.exception.ErrorMessage;

/**
 * 公共错误常量定义类
 *
 * @author Qianliang Zhang
 */
public final class CommonError {
    /**
     * 未知原因造成接口请求失败
     */
    public static final ErrorMessage UNKNOWN_ERROR = new ErrorMessage(-1001, "unknown error");

    /**
     * 接口请求时，参数的值类型不对
     */
    public static final ErrorMessage ILLEGAL_REQUEST_PARAM = new ErrorMessage(-1002,
            "illegal request param");

    /**
     * 服务过载或者请求繁忙时错误。比如线程任务过载等错误。
     */
    public static final ErrorMessage SERVER_BUSY = new ErrorMessage(-1003,"server busy");
}
