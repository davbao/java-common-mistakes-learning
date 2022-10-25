package davbao.github.io.exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author davbao
 * @date 2022/10/25
 */
@Slf4j
@RestController
@RequestMapping("handle/exception")
public class HandleExceptionController {

    @GetMapping("exception")
    public void exception(@RequestParam("business") boolean b) {
        if (b) {
            throw new BusinessException("订单不存在", 2001);
        } else {
            throw new RuntimeException("系统错误");
        }
    }

    @GetMapping("wrong1")
    public void wrong1() {
        try {
            readFile();
        } catch (Exception e) {
            // 错误原因:原始异常信息丢失
            throw new RuntimeException("系统忙请稍后重试");
        }
    }

    @GetMapping("wrong2")
    public void wrong2() {
        try {
            readFile();
        } catch (IOException e) {
            // 错误原因:只记录了异常消息，却丢失了异常的类型、栈等重要信息
            log.error("文件读取错误，{}", e.getMessage());
            throw new RuntimeException("系统忙请稍后重试");
        }
    }

    @GetMapping("wrong3")
    public void wrong3() {
        try {
            readFile();
        } catch (Exception e) {
            log.error("文件读取错误", e);
            // 错误原因:抛出异常时不指定任何消息,被 ExceptionHandler 拦截到后输出了null,
            // 这里的 null 非常容易引起误解。如果按照空指针问题排查可能花费很久，但是实际上是异常的 message 为空。
            throw new RuntimeException();
        }
    }

    @GetMapping("right1")
    public void right1() {
        try {
            readFile();
        } catch (IOException e) {
            log.error("文件读取错误", e);
            throw new RuntimeException("系统忙请稍后再试");
        }
    }

    @GetMapping("right2")
    public void right2() {
        try {
            readFile();
        } catch (IOException e) {
            throw new RuntimeException("系统忙请稍后再试", e);
        }
    }

    private void readFile() throws IOException {
        Files.readAllLines(Paths.get("a_file"));
    }

}
