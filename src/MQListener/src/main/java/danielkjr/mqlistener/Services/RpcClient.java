package danielkjr.mqlistener.Services;


import danielkjr.mqlistener.Model.MQAction;
import danielkjr.mqlistener.Model.StoreCommand;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RpcClient {


    private final RabbitTemplate template;


    private final DirectExchange exchange;

    public RpcClient(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.template = rabbitTemplate;
        this.exchange = directExchange;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void createAndDispatch(String message) {
        UUID actionId = UUID.randomUUID();
        StoreCommand command = new StoreCommand(actionId, message, MQAction.CREATE);
        UUID response = (UUID) template.convertSendAndReceive(exchange.getName(), "rpc", command);
        System.out.println(exchange.getName() + " sent to " + actionId + "with command:" + command);
        System.out.println("Response: "+response);
    }


}