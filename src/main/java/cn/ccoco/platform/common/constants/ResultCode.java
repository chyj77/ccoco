package cn.ccoco.platform.common.constants;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 业务代码枚举
 *
 * @author Chill
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS(HttpResponseStatus.OK.code(), "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(HttpResponseStatus.BAD_REQUEST.code(), "业务异常"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(HttpResponseStatus.UNAUTHORIZED.code(), "请求未授权"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(HttpResponseStatus.NOT_FOUND.code(), "404 没找到请求"),

    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE(HttpResponseStatus.BAD_REQUEST.code(), "消息不能读取"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(HttpResponseStatus.METHOD_NOT_ALLOWED.code(), "不支持当前请求方法"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.code(), "不支持当前媒体类型"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(HttpResponseStatus.FORBIDDEN.code(), "请求被拒绝"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), "服务器异常"),

    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS(HttpResponseStatus.BAD_REQUEST.code(), "缺少必要的请求参数"),

    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_ERROR(HttpResponseStatus.BAD_REQUEST.code(), "请求参数类型错误"),

    /**
     * 请求参数绑定错误
     */
    PARAM_BIND_ERROR(HttpResponseStatus.BAD_REQUEST.code(), "请求参数绑定错误"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(HttpResponseStatus.BAD_REQUEST.code(), "参数校验失败"),
    ;

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

    public static String getValue(int code){
        for (ResultCode enums : ResultCode.values()) {
            if (enums.code== code) {
                return enums.message;
            }
        }
        return null;
    }
}
