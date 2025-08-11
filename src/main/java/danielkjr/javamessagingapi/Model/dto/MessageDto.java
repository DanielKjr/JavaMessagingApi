package danielkjr.javamessagingapi.Model.dto;


import danielkjr.mqloggingclient.model.LoggingSeverity;

public record MessageDto(String message, LoggingSeverity severity) {


    public String getMessage() { return message; }
    public LoggingSeverity getSeverity() { return severity; }
}