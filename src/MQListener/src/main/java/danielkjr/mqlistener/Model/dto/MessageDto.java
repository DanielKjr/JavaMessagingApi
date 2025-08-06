package danielkjr.mqlistener.Model.dto;

import danielkjr.mqlistener.Model.LoggingSeverity;

public record MessageDto(String message, LoggingSeverity severity) {


    public String getMessage() { return message; }
    public LoggingSeverity getSeverity() { return severity; }
}
