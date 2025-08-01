package danielkjr.javamessagingapi.Model;

import java.util.UUID;

public record StoreCommand(UUID actionId, String message, MQAction action) {}
