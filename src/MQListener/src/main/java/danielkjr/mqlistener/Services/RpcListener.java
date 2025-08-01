package danielkjr.mqlistener.Services;


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
