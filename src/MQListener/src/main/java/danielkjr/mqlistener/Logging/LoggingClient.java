package danielkjr.mqlistener.Logging;

import danielkjr.mqlistener.Model.LoggingSeverity;
import danielkjr.mqlistener.Model.dto.LogMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class LoggingClient {

    private final RabbitTemplate rabbitTemplate;

    public LoggingClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(Duration.ofSeconds(5).toMillis());
    }


    public void logAndAwaitAck(String contextId, String logMessage, LoggingSeverity severity, String sender) {
        LogMessageDto message = new LogMessageDto(contextId, logMessage, severity, sender);

        Boolean acknowledge = rabbitTemplate.convertSendAndReceiveAsType(
                "logging.exchange",
                "logging.route",
                message,
                new ParameterizedTypeReference<Boolean>() {
                });

        if (Boolean.FALSE.equals(acknowledge) || acknowledge == null) {
            throw new RuntimeException("Log acknowledge failed");
        }
    }
}
