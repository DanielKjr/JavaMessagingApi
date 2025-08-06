package danielkjr.mqlogger.Services;

import danielkjr.mqlogger.Model.dto.LogMessageDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LoggingListener {


    private final LoggingService loggingService;


    public LoggingListener(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @RabbitListener(queues = "log.queue")
    public void handleMessage(LogMessageDto dto) {
        loggingService.logMessage(dto);
    }
}
