package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.MessageBroker.Clients.RpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("CCC")
public class CommandChainController {



    private final RpcClient rpcClient;
    public CommandChainController( RpcClient rpcClient) {

        this.rpcClient = rpcClient;
    }


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void newEntry(String message) {
        rpcClient.createAndDispatch(message);
    }



}
