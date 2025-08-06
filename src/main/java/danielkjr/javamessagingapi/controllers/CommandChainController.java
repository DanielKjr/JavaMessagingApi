package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.MessageBroker.Clients.RpcClient;
import danielkjr.javamessagingapi.Model.dto.MessageDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("CCC")
public class CommandChainController {


    private final RpcClient rpcClient;

    public CommandChainController(RpcClient rpcClient) {

        this.rpcClient = rpcClient;
    }


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void newEntry(@RequestBody MessageDto dto) {
        rpcClient.createAndDispatch(dto);
    }


}
