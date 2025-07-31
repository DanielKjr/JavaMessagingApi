package danielkjr.javamessagingapi.Service;

import danielkjr.javamessagingapi.Model.ActionTrack;
import danielkjr.javamessagingapi.Model.MQAction;
import danielkjr.javamessagingapi.Model.PlaceHolderEntry;
import danielkjr.javamessagingapi.Model.Status;
import danielkjr.javamessagingapi.Repositories.ActionTrackRepository;
import danielkjr.javamessagingapi.Repositories.PlaceHolderEntryRepository;
import danielkjr.javamessagingapi.controllers.StoreCommand;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EntryService {

    private final PlaceHolderEntryRepository entryRepo;
    private final ActionTrackRepository actionRepo;
    private final RabbitTemplate template;
    private final DirectExchange exchange;

    public EntryService(PlaceHolderEntryRepository entryRepo,
                        ActionTrackRepository actionRepo,
                        RabbitTemplate template,
                        DirectExchange exchange) {
        this.entryRepo = entryRepo;
        this.actionRepo = actionRepo;
        this.template = template;
        this.exchange = exchange;
    }


    public UUID createAndDispatch(String message) {
        UUID actionId = UUID.randomUUID();

        StoreCommand command = new StoreCommand(actionId, message, MQAction.CREATE);

        template.convertAndSend(exchange.getName(), "rpc.requests", command);
        System.out.println(exchange.getName() + " sent to " + actionId + "with command:" + command);
        return actionId; // return early, before persistence
    }



//    public UUID createAndDispatch(String message) {
//        PlaceHolderEntry entry = new PlaceHolderEntry();
//        entry.setMessage(message);
//
//        ActionTrack action = new ActionTrack();
//        action.setStatus(Status.CREATED);
//        action.setCurrentAction(MQAction.CREATE);
//        action.setEntry(entry);
//
//        entry.setActions(List.of(action));
//        entryRepo.save(entry);
//
//        // Dispatch the message
//        template.convertAndSend(exchange.getName(), "rpc", action.getId().toString());
//
//        return action.getId();
//    }

    public Map<String, ? extends Comparable<? extends Comparable<?>>> getStatus(UUID actionId) {
        return actionRepo.findById(actionId)
                .map(action -> Map.of(
                        "status", action.getStatus(),
                        "action", action.getCurrentAction(),
                        "entryId", action.getEntry().getId()
                ))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteById(UUID actionId) {
        actionRepo.deleteById(actionId);
    }

    public PlaceHolderEntry findById(UUID id) {
        return entryRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<PlaceHolderEntry> findAll() {
        return entryRepo.findAll();
    }


}

