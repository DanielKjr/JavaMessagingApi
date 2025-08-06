package danielkjr.javamessagingapi.Model.dto;



import danielkjr.javamessagingapi.Model.LoggingSeverity;

import java.io.Serializable;


public record LogMessageDto(String contextId, String message, LoggingSeverity severity, String sender) implements Serializable {}

