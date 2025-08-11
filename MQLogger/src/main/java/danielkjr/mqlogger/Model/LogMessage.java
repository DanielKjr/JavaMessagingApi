package danielkjr.mqlogger.Model;

import danielkjr.mqloggingclient.model.LoggingSeverity;
import jakarta.persistence.*;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "Logs", schema = "mqlogs")
public class LogMessage {

    @Id
    @GeneratedValue
    private UUID logMessageId;
    private String contextId;
    private String message;
    @Enumerated(EnumType.STRING)
    private LoggingSeverity severity;
    private String sender;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime timeStamp;

    public UUID getLogMessageId() {
        return logMessageId;
    }

    public String getContextId() {
        return contextId;
    }

    public String getMessage() {
        return message;
    }

    public LoggingSeverity getSeverity() {
        return severity;
    }

    public String getSender() {
        return sender;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    @PrePersist
    protected void onCreate() {
        timeStamp = ZonedDateTime.now(ZoneId.of("Europe/Oslo"));
        ;
    }

    public LogMessage() {
    }

    public LogMessage(String contextId, String message, LoggingSeverity severity, String sender) {
        this.contextId = contextId;
        this.message = message;
        this.severity = severity;
        this.sender = sender;
    }
}
