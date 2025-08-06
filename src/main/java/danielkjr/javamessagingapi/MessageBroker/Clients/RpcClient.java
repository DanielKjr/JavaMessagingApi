package danielkjr.javamessagingapi.MessageBroker.Clients;


import danielkjr.javamessagingapi.Model.MQAction;
import danielkjr.javamessagingapi.Model.StoreCommand;
import danielkjr.javamessagingapi.Model.dto.MessageDto;
import danielkjr.javamessagingapi.Utility.NameProvider;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RpcClient {


    private final RabbitTemplate template;
    private final NameProvider nameProvider;

    private final DirectExchange exchange;

    public RpcClient(RabbitTemplate rabbitTemplate, NameProvider nameProvider, DirectExchange directExchange) {
        this.template = rabbitTemplate;
        this.nameProvider = nameProvider;
        this.exchange = directExchange;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void createAndDispatch(MessageDto message) {
        UUID actionId = UUID.randomUUID();
        StoreCommand command = new StoreCommand(actionId, message.getMessage(), MQAction.CREATE, nameProvider.getName(), message.getSeverity());
        UUID response = (UUID) template.convertSendAndReceive(exchange.getName(), "rpc", command);
        System.out.println(exchange.getName() + " sent to " + actionId + "with command:" + command);
        System.out.println("Response: " + response);
    }


}
