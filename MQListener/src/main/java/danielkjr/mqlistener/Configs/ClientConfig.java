package danielkjr.mqlistener.Configs;

import danielkjr.mqlistener.Services.RpcClient;
import danielkjr.mqloggingclient.nameprovider.NameProvider;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Configuration
public class ClientConfig {

    public static final String EXCHANGE_NAME = "rpc";
    public static final String REQUEST_QUEUE = "rpc.requests";

    public ClientConfig() {
    }



    @Bean
    public RpcClient rpcClient(RabbitTemplate rabbitTemplate, DirectExchange rpcExchange, NameProvider nameProvider) {
        return new RpcClient(rabbitTemplate, rpcExchange, nameProvider);
    }

    @Bean
    @Qualifier(value = "RPC")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(jacksonMessageConverter);
        template.setReplyTimeout(Duration.ofSeconds(5).toMillis());
        return template;
    }

    @Bean
    public DirectExchange rpcExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue rpcRequestQueue() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean
    public Binding rpcBinding(Queue rpcRequestQueue, DirectExchange rpcExchange) {
        return BindingBuilder.bind(rpcRequestQueue).to(rpcExchange).with("rpc");
    }

}

