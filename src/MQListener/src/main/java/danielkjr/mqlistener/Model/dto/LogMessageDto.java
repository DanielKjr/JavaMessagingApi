package danielkjr.mqlistener.Model.dto;

import danielkjr.mqlistener.Model.LoggingSeverity;

import java.io.Serializable;

public record LogMessageDto(String contextId, String message, LoggingSeverity severity, String sender) implements Serializable {}
