package danielkjr.mqloggingclient.config;

import danielkjr.mqloggingclient.client.LoggingClient;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RabbitLoggingAutoConfiguration {


    @Bean
    @Qualifier(value = "Logging")
    public MessageConverter jacksonLoggingMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Qualifier(value = "Logging.Queue")
    public Queue loggingQueue(RabbitLoggingProperties props) {
        return new Queue(props.getQueue(), true);
    }

    @Bean
    @Qualifier(value = "Logging.Direct")
    public DirectExchange loggingExchange(RabbitLoggingProperties props) {
        return new DirectExchange(props.getExchange(), true, false);
    }

    @Bean
    @Qualifier(value = "Logging.Binding")
    public Binding loggingBinding(Queue loggingQueue, DirectExchange loggingExchange, RabbitLoggingProperties props) {
        return BindingBuilder.bind(loggingQueue).to(loggingExchange).with(props.getRoutingKey());
    }

    @Bean
    public RabbitLoggingProperties props (){
        return new RabbitLoggingProperties();
    }

    @Bean
    @Qualifier(value = "Logging")
    public RabbitTemplate rabbitLogTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonLoggingMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonLoggingMessageConverter);
        template.setReplyTimeout(Duration.ofSeconds(5).toMillis());
        return template;
    }
    @Bean
    public LoggingClient loggingClient(RabbitTemplate rabbitLogTemplate, RabbitLoggingProperties props) {
        return new LoggingClient(rabbitLogTemplate, props);
    }

}
