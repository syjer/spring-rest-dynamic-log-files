import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Main {

    private static final Logger LOG = LogManager.getLogger();

    @RequestMapping(value = "/{app:[a-zA-Z0-9_\\-]+}/{env:[a-zA-Z0-9_\\-]+}/{mode:[a-zA-Z0-9_\\-]+}")
    String log(@PathVariable("app") String app, @PathVariable("env") String env, @PathVariable("mode") String mode, @RequestBody(required = false)  String payload) {
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put("application", app).put("environment", env).put("mode", mode)) {
            LOG.info("app: {}, env: {}, mode: {}, payload: {}", app, env, mode, payload);
        }
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
