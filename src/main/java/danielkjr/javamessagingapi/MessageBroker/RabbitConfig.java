//package danielkjr.javamessagingapi.MessageBroker;
//
//
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//    public static final String QUEUE_NAME = "db.operations";
//
////    @Bean
////    public Queue dbOperationsQueue(){
////        return new Queue(QUEUE_NAME);
////    }
////
////
////    @Bean
////    public Jackson2JsonMessageConverter messageConverter() {
////        return new Jackson2JsonMessageConverter();
////    }
////
////    @Bean
////    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
////                                         MessageConverter messageConverter) {
////
////        RabbitTemplate template = new RabbitTemplate(connectionFactory);
////        template.setMessageConverter(messageConverter);
////        return template;
////    }
//}
