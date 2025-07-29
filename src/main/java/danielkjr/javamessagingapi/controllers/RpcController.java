package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.MessageBroker.Clients.RpcClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("RPC")
public class RpcController {

    private final RpcClient rpcClient;

    public RpcController(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }


    @GetMapping("/send")
    public void sendMessage() {
        rpcClient.send();
    }


}
