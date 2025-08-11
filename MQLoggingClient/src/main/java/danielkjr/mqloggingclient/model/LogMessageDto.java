package danielkjr.mqloggingclient.model;


import java.io.Serializable;


public record LogMessageDto(String contextId, String message, LoggingSeverity severity,
                            String sender) implements Serializable {
}

