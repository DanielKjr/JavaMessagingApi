package danielkjr.mqlistener.Services;


import danielkjr.mqlistener.Logging.LoggingClient;
import danielkjr.mqlistener.Model.ActionTrack;
import danielkjr.mqlistener.Model.LoggingSeverity;
import danielkjr.mqlistener.Model.PlaceHolderEntry;
import danielkjr.mqlistener.Model.StoreCommand;
import danielkjr.mqlistener.Model.dto.LogMessageDto;
import danielkjr.mqlistener.Repository.PlaceHolderEntryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service
public class RpcListener {
    private final PlaceHolderEntryRepository entryRepo;
    private final LoggingClient loggingClient;
    public RpcListener(PlaceHolderEntryRepository entryRepo, LoggingClient loggingClient) {
        this.entryRepo = entryRepo;
        this.loggingClient = loggingClient;
    }



    @RabbitListener(queues = "rpc.requests")
    public UUID acknowledge(StoreCommand command) {

        loggingClient.logAndAwaitAck(
                command.actionId().toString(),
                "Received command: " + command.message(), LoggingSeverity.INFO, command.sender()
        );
        System.out.println("Received: " + command);
        PlaceHolderEntry entry = new PlaceHolderEntry(command.message());
        ActionTrack action = new ActionTrack(command);
        entry.addAction(action);
        entryRepo.save(entry);
        return entry.getId();
    }


    @RabbitListener(queues = "logging.queue")
    public boolean handleLog(LogMessageDto message) {
        // Format the log line
        String logLine = String.format("LOG [%s]: %s (sender: %s)%n",
                message.contextId(), message.message(), message.sender());

        // Define the file path (relative or absolute)
        String filePath = "/app/logs/temp-log.txt";


        // Write to the file (append mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(logLine);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // return false on failure to indicate log failure
        }

        return true;
    }



}
