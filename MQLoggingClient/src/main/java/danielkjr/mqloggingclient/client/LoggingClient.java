package danielkjr.mqloggingclient.client;

import danielkjr.mqloggingclient.config.RabbitLoggingProperties;
import danielkjr.mqloggingclient.model.LogMessageDto;
import danielkjr.mqloggingclient.model.LoggingSeverity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;


public class LoggingClient {

    private final RabbitTemplate rabbitLogTemplate;
    private final RabbitLoggingProperties props;

    public LoggingClient(@Qualifier(value = "Logging")RabbitTemplate rabbitLogTemplate, @Qualifier("Logging") RabbitLoggingProperties rabbitLoggingProperties) {
        this.rabbitLogTemplate = rabbitLogTemplate;
        this.props = rabbitLoggingProperties;
    }


    public void log(String contextId, String logMessage, LoggingSeverity severity, String sender) {
        LogMessageDto message = new LogMessageDto(contextId, logMessage, severity, sender);
        rabbitLogTemplate.convertAndSend(
                props.getExchange(),
                props.getRoutingKey(),
                message
        );
    }
    public void log(LogMessageDto message) {
        rabbitLogTemplate.convertAndSend(
                props.getExchange(),
                props.getRoutingKey(),
                message
        );
    }
}