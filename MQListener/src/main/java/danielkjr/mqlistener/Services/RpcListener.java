package danielkjr.mqlistener.Services;



import danielkjr.mqlistener.Model.PlaceHolderEntry;
import danielkjr.mqlistener.Repository.PlaceHolderEntryRepository;


import danielkjr.mqloggingclient.client.LoggingClient;
import danielkjr.mqloggingclient.model.LogMessageDto;
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
    public UUID acknowledge(LogMessageDto msg) {
        System.out.println("Received: " + msg);
        loggingClient.log(msg);
        PlaceHolderEntry entry = new PlaceHolderEntry(msg.message());
        entryRepo.save(entry);
        return entry.getId();
    }


}
