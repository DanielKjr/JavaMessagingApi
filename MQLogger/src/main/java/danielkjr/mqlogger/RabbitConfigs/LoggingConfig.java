package danielkjr.mqlogger.RabbitConfigs;


import danielkjr.mqlogger.Services.LoggingListener;
import danielkjr.mqloggingclient.config.RabbitLoggingProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LoggingConfig {

    private final RabbitLoggingProperties props;

    public LoggingConfig(RabbitLoggingProperties props) {
        this.props = props;
    }

    @Bean
    public TopicExchange logExchange() {
        return new TopicExchange(props.getExchange());
    }

    @Bean
    public Queue queue() {
        return new Queue(props.getQueue());
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(LoggingListener loggingListener, @Qualifier("Logging") MessageConverter messageConverter) {
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
                                         @Qualifier("Logging") MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setReplyTimeout(Duration.ofSeconds(5).toMillis());
        return rabbitTemplate;
    }

//    @Bean
//    public MessageConverter jacksonMessageConverter() {
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//        Map<String, Class<?>> idClassMapping = new HashMap<>();

//        idClassMapping.put("danielkjr.mqlistener.mqloggingclient.model.LogMessageDto", danielkjr.mqlogger.Model.dto.LogMessageDto.class);
//        idClassMapping.put("danielkjr.mqloggingclient.Model.LoggingSeverity", danielkjr.mqlogger.Model.LoggingSeverity.class);
//        typeMapper.setIdClassMapping(idClassMapping);
//        converter.setJavaTypeMapper(typeMapper);
//        return converter;
//    }
}
