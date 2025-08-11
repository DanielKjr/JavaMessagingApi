package danielkjr.javamessagingapi.MessageBroker.Configurations;


import danielkjr.javamessagingapi.MessageBroker.Clients.RpcClient;

import danielkjr.mqloggingclient.nameprovider.NameProvider;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://www.rabbitmq.com/tutorials/tutorial-six-spring-amqp
@Configuration
public class ClientConfig {


    public static final String EXCHANGE_NAME = "rpc";
    public static final String REQUEST_QUEUE = "rpc.requests";

    public ClientConfig() {
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



    @Bean
    public RpcClient rpcClient(RabbitTemplate rabbitTemplate, DirectExchange rpcExchange, NameProvider nameProvider) {
        return new RpcClient(rabbitTemplate,nameProvider, rpcExchange);
    }


    @Bean
    @Qualifier(value = "RCP")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    @Qualifier(value = "RCP")
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
