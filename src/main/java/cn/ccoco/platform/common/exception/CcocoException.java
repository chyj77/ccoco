package cn.ccoco.platform.common.exception;

/**
 * 系统内部异常
 *
 * @author CCoco
 */
public class CcocoException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public CcocoException(String message) {
        super(message);
    }
}
