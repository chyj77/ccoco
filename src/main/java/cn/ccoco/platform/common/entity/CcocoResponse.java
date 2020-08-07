package cn.ccoco.platform.common.entity;

import cn.ccoco.platform.common.constants.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author CCoco
 */
public class CcocoResponse<T> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;

    @ApiModelProperty(
            value = "状态码",
            required = true
    )
    private int code;
    @ApiModelProperty(
            value = "是否成功",
            required = true
    )
    private boolean success;
    @ApiModelProperty("承载数据")
    private T data;
    @ApiModelProperty(
            value = "返回消息",
            required = true
    )
    private String msg;

    private CcocoResponse(int resultCode) {
        this(resultCode, null, ResultCode.getValue(resultCode));
    }

    private CcocoResponse(int resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private CcocoResponse(int resultCode, T data) {
        this(resultCode, data, ResultCode.getValue(resultCode));
    }

    private CcocoResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.getCode() == code;
    }

    public static boolean isSuccess(@Nullable CcocoResponse<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.getCode(), x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable CcocoResponse<?> result) {
        return !isSuccess(result);
    }

    public static <T> CcocoResponse<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> CcocoResponse<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    public static <T> CcocoResponse<T> data(int code, T data, String msg) {
        return new CcocoResponse(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> CcocoResponse<T> success(String msg) {
        return new CcocoResponse(ResultCode.SUCCESS.getCode(), msg);
    }
    public static <T> CcocoResponse<T> success(T data) {
        return new CcocoResponse(ResultCode.SUCCESS.getCode(), data,"操作成功");
    }

    public static <T> CcocoResponse<T> success(int resultCode) {
        return new CcocoResponse(resultCode);
    }

    public static <T> CcocoResponse<T> success() {
        return new CcocoResponse(ResultCode.SUCCESS.getCode());
    }

    public static <T> CcocoResponse<T> success(int resultCode, String msg) {
        return new CcocoResponse(resultCode, msg);
    }

    public static <T> CcocoResponse<T> fail(String msg) {
        return new CcocoResponse(ResultCode.FAILURE.getCode(), msg);
    }

    public static <T> CcocoResponse<T> fail(int code, String msg) {
        return new CcocoResponse(code, (Object)null, msg);
    }

    public static <T> CcocoResponse<T> fail(int resultCode) {
        return new CcocoResponse(resultCode);
    }


    public static <T> CcocoResponse<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CcocoResponse(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }

    public CcocoResponse() {
    }
}
