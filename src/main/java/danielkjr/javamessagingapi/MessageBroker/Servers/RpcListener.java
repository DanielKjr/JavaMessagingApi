package danielkjr.javamessagingapi.MessageBroker.Servers;

import danielkjr.javamessagingapi.Model.ActionTrack;
import danielkjr.javamessagingapi.Model.PlaceHolderEntry;
import danielkjr.javamessagingapi.Repositories.PlaceHolderEntryRepository;
import danielkjr.javamessagingapi.controllers.StoreCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RpcListener {
    private final PlaceHolderEntryRepository entryRepo;

    public RpcListener(PlaceHolderEntryRepository entryRepo) {
        this.entryRepo = entryRepo;
    }



    @RabbitListener(queues = "rpc.requests")
    public UUID acknowledge(StoreCommand command) {
        System.out.println("Received: " + command);
        PlaceHolderEntry entry = new PlaceHolderEntry(command.message());
        ActionTrack action = new ActionTrack(command);
        entry.addAction(action);
        entryRepo.save(entry);
        return entry.getId();
    }


}
