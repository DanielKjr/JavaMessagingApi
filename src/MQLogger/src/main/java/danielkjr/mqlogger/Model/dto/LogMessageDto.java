package danielkjr.mqlogger.Model.dto;

import danielkjr.mqlogger.Model.LoggingSeverity;

import java.io.Serializable;


public record LogMessageDto(String contextId, String message, LoggingSeverity severity,
                            String sender) implements Serializable {
}

