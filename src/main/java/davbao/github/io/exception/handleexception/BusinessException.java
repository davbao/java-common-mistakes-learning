package davbao.github.io.exception.handleexception;

/**
 * @author davbao
 * @date 2022/10/25
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
