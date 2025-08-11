package danielkjr.mqlogger.Services;

import danielkjr.mqlogger.Model.LogMessage;
import danielkjr.mqlogger.Repository.LoggingRepository;
import danielkjr.mqloggingclient.model.LogMessageDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private final LoggingRepository loggingRepository;

    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }


    @Transactional
    public void logMessage(LogMessageDto logMessage) {
        LogMessage msg = new LogMessage(logMessage.contextId(), logMessage.message(), logMessage.severity(), logMessage.sender());
        loggingRepository.save(msg);
    }
}
