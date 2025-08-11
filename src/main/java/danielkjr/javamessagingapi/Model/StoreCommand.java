package danielkjr.javamessagingapi.Model;



import danielkjr.mqloggingclient.model.LoggingSeverity;

import java.util.UUID;

public record StoreCommand(UUID actionId, String message, MQAction action, String sender, LoggingSeverity severity) {}
