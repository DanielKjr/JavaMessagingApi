package danielkjr.mqlistener.Configs;

import danielkjr.mqlistener.Utility.NameProvider;
import danielkjr.mqlistener.Logging.LoggingClient;
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

import java.time.Duration;
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
    public NameProvider nameProvider(org.springframework.core.env.Environment env) {
        return new NameProvider(env);
    }

    @Bean
    public LoggingClient loggingClient(RabbitTemplate rabbitTemplate) {
        return new LoggingClient(rabbitTemplate);
    }

    @Bean
    public RpcListener rpcListener(LoggingClient loggingClient) {
        return new RpcListener(entryRepo, loggingClient);
    }

    @Bean
    public RpcClient rpcClient(RabbitTemplate rabbitTemplate, DirectExchange rpcExchange, NameProvider nameProvider) {
        return new RpcClient(rabbitTemplate, rpcExchange, nameProvider);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
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
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        // the producer sends a StoreCommand object, as it is in the other project we have to register an alias
        // for the broker to be able to translate it, same with mqaction
        idClassMapping.put("danielkjr.javamessagingapi.Model.StoreCommand", danielkjr.mqlistener.Model.StoreCommand.class);
        idClassMapping.put("danielkjr.javamessagingapi.Model.MQAction", danielkjr.mqlistener.Model.MQAction.class);
        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public Queue loggingQueue() {
        return new Queue("logging.queue");
    }

    @Bean
    public DirectExchange loggingExchange() {
        return new DirectExchange("logging.exchange");
    }

    @Bean
    public Binding loggingBinding(Queue loggingQueue, DirectExchange loggingExchange) {
        return BindingBuilder.bind(loggingQueue).to(loggingExchange).with("logging.route");
    }
}

