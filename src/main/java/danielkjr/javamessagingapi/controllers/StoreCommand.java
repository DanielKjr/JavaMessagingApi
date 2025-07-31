package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.Model.MQAction;

import java.util.UUID;

public record StoreCommand(UUID actionId, String message, MQAction action) {}
