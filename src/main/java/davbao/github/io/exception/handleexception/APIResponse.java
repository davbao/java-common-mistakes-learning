package davbao.github.io.exception.handleexception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author davbao
 * @date 2022/10/25
 */
@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
