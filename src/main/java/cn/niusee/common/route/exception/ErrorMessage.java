package cn.niusee.common.route.exception;

/**
 * 错误定义类
 *
 * @author Qianliang Zhang
 */
public final class ErrorMessage {

    /**
     * 错误代码
     */
    private final int errorCode;

    /**
     * 错误信息
     */
    private final String errorMsg;

    public ErrorMessage(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码，负整数代码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息。错误描述信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }
}
