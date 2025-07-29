package danielkjr.javamessagingapi.MessageBroker.Servers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RpcServer {

    @RabbitListener(queues = "rpc.requests")
    public Integer acknowledge(Integer message){
        return  message;
    }
}
