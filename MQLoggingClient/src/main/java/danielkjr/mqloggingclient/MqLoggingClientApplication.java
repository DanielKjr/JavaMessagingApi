package danielkjr.mqloggingclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(danielkjr.mqloggingclient.config.RabbitLoggingAutoConfiguration.class)
@SpringBootApplication
public class MqLoggingClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqLoggingClientApplication.class, args);
    }

}
