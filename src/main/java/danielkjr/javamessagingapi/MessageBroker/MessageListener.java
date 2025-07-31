//package danielkjr.javamessagingapi.MessageBroker;
//
//import danielkjr.javamessagingapi.Service.MQLogService;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageListener {
//
//    private final MQLogService mqLogService;
//
//    public MessageListener(MQLogService mqLogService) {
//        this.mqLogService = mqLogService;
//    }
//
//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
//    public void handleMessage(dbOperationMessage message ) {
//        switch (message.getOperation()) {
//            case "insert":
//                mqLogService.addLog(message);
//                break;
//            case "select":
//                mqLogService.getLogs();
//                break;
//            case "patch":
//                mqLogService.patchLog(message);
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported operation: " + message.getOperation());
//        }
//    }
//}
