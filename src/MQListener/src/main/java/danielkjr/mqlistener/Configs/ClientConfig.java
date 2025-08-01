package danielkjr.mqlistener.Configs;

import danielkjr.mqlistener.Repository.PlaceHolderEntryRepository;
import danielkjr.mqlistener.Services.RpcClient;
import danielkjr.mqlistener.Services.RpcListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientConfig {

    private final PlaceHolderEntryRepository entryRepo;
    public static final String EXCHANGE_NAME = "rpc";
    public static final String REQUEST_QUEUE = "rpc.requests";

    public ClientConfig(PlaceHolderEntryRepository entryRepo) {
        this.entryRepo = entryRepo;
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
    public RpcListener rpcListener() {
        return new RpcListener(entryRepo);
    }

    @Bean
    public RpcClient rpcClient(RabbitTemplate rabbitTemplate, DirectExchange rpcExchange) {
        return new RpcClient(rabbitTemplate, rpcExchange);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RpcListener rpcListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(rpcListener, "acknowledge");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter adapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames("rpc.requests");
        container.setMessageListener(adapter);
        return container;
    }


    @Bean
    public MessageConverter jacksonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        // the producer sends a StoreCommand object, as it is in the other project we have to register an alias
        // for the broker to be able to translate it
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("danielkjr.javamessagingapi.controllers.StoreCommand", danielkjr.mqlistener.Model.StoreCommand.class);

        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }


}
