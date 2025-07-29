package danielkjr.javamessagingapi.MessageBroker.Clients;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RpcClient {


    private final RabbitTemplate template;


    private final DirectExchange exchange;

    public RpcClient(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.template = rabbitTemplate;
        this.exchange = directExchange;
    }

    int start = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(){
        System.out.println(" [x] Requesting (" + start + ")");
        Integer response = (Integer) template.convertSendAndReceive
                (exchange.getName(), "rpc", start++);
        System.out.println(" [.] Got '" + response + "'");
    }
}
