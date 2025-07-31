//package danielkjr.javamessagingapi.MessageBroker;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public MessageProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendMessage(dbOperationMessage message) {
//        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, message);
//    }
//}
