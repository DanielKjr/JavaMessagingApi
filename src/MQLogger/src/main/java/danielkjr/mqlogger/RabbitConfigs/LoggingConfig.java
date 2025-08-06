package danielkjr.mqlogger.RabbitConfigs;


import danielkjr.mqlogger.Services.LoggingListener;
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
public class LoggingConfig {

    private final RabbitLoggingProperties props;

    public LoggingConfig(RabbitLoggingProperties props) {
        this.props = props;
    }

    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange(props.getExchange());
    }

    @Bean
    public Queue queue() {
        return new Queue(props.getQueue());
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(LoggingListener loggingListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(loggingListener, "handleMessage");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter adapter) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(props.getQueue());
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setReplyTimeout(Duration.ofSeconds(5).toMillis());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("danielkjr.mqlistener.Model.dto.LogMessageDto", danielkjr.mqlogger.Model.dto.LogMessageDto.class);
        idClassMapping.put("danielkjr.mqlistener.Model.LoggingSeverity", danielkjr.mqlogger.Model.LoggingSeverity.class);
        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
