package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.MessageBroker.Clients.RpcClient;
import danielkjr.javamessagingapi.Model.MQAction;
import danielkjr.javamessagingapi.Model.PlaceHolderEntry;
import danielkjr.javamessagingapi.Service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("CCC")
public class CommandChainController {


    private final EntryService entryService;
    private final RpcClient rpcClient;
    public CommandChainController(EntryService entryService, RpcClient rpcClient) {
        this.entryService = entryService;
        this.rpcClient = rpcClient;
    }


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void newEntry(String message) {
        rpcClient.createAndDispatch(message);
    }



    @GetMapping("/status")
    public Map<String, ? extends Comparable<? extends Comparable<?>>> getStatus(UUID id) {
        return entryService.getStatus(id);
    }

    @GetMapping("/id")
    public PlaceHolderEntry getEntryById(UUID id) {
        return entryService.findById(id);
    }
}
