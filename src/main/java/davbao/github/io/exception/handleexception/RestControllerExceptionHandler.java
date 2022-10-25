package davbao.github.io.exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author davbao
 * @date 2022/10/25
 */
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private static final int GENERIC_SERVICE_ERROR_CODE = 2000;
    private static final String GENERIC_SERVICE_ERROR_MESSAGE = "服务器忙，请稍后重试";

    @ExceptionHandler
    public APIResponse handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException) ex;
            log.warn(String.format("访问 %s -> %s 出现业务异常!", req.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, exception.getCode(), exception.getMessage());
        } else {
            log.error(String.format("访问 %s -> %s 出现系统异常!", req.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, GENERIC_SERVICE_ERROR_CODE, GENERIC_SERVICE_ERROR_MESSAGE);
        }
    }
}
