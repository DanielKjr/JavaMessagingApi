package danielkjr.mqlistener.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum LoggingSeverity {
    WARN,
    ERROR,
    INFO
}
