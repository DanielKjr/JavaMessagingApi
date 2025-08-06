package danielkjr.mqlistener.Model;


import java.util.UUID;

public record StoreCommand(UUID actionId, String message, MQAction action, String sender, LoggingSeverity severity) {
}

