package danielkjr.mqlistener.Services;


import danielkjr.mqlistener.Model.MQAction;
import danielkjr.mqlistener.Model.StoreCommand;
import danielkjr.mqloggingclient.nameprovider.NameProvider;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RpcClient {


    private final RabbitTemplate template;
    private final DirectExchange exchange;
    private final NameProvider nameProvider;

    public RpcClient(@Qualifier(value = "RPC")RabbitTemplate rabbitTemplate, DirectExchange directExchange, NameProvider nameProvider) {
        this.template = rabbitTemplate;
        this.exchange = directExchange;
        this.nameProvider = nameProvider;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void createAndDispatch(StoreCommand cmd) {
        UUID actionId = UUID.randomUUID();
        StoreCommand command = new StoreCommand(actionId, cmd.message(), MQAction.CREATE, nameProvider.getName(), cmd.severity());
        UUID response = (UUID) template.convertSendAndReceive(exchange.getName(), "rpc", command);
    }


}