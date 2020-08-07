package cn.ccoco.platform.common.entity;

/**
 * 常量
 *
 * @author CCoco
 */
public interface CCocoConstant {

    /**
     * 注册用户角色ID
     */
    Long REGISTER_ROLE_ID = 2L;

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "asc";


    /**
     * 验证码 Session Key
     */
    String CODE_PREFIX = "ccoco_captcha_";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    /**
     * 异步线程池名称
     */
    String ASYNC_POOL = "ccocoAsyncThreadPool";

    /**
     * 开发环境
     */
    String DEVELOP = "dev";

    /**
     * Windows 操作系统
     */
    String SYSTEM_WINDOWS = "windows";
}
