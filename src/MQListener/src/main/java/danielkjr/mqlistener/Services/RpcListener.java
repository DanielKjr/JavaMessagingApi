package danielkjr.mqlistener.Services;


import danielkjr.mqlistener.Logging.LoggingClient;
import danielkjr.mqlistener.Model.ActionTrack;
import danielkjr.mqlistener.Model.PlaceHolderEntry;
import danielkjr.mqlistener.Model.StoreCommand;
import danielkjr.mqlistener.Repository.PlaceHolderEntryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

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
        loggingClient.log(
                command.actionId().toString(),
                command.message(), command.severity(), command.sender()
        );
        System.out.println("Received: " + command);
        PlaceHolderEntry entry = new PlaceHolderEntry(command.message());
        ActionTrack action = new ActionTrack(command);
        entry.addAction(action);
        entryRepo.save(entry);
        return entry.getId();
    }


}
