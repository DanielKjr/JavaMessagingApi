package danielkjr.mqlistener.Logging;

import danielkjr.mqlistener.Model.LoggingSeverity;
import danielkjr.mqlistener.Model.dto.LogMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class LoggingClient {

    private final RabbitTemplate rabbitTemplate;


    public LoggingClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(Duration.ofSeconds(5).toMillis());
    }


    public void log(String contextId, String logMessage, LoggingSeverity severity, String sender) {
        LogMessageDto message = new LogMessageDto(contextId, logMessage, severity, sender);
        rabbitTemplate.convertAndSend(
                "log.exchange",
                "log.route",
                message
        );

    }
}
