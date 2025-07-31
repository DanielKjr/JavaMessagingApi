package danielkjr.javamessagingapi.MessageBroker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    private final static String QUEUE_NAME = "rpc";
    public static void send(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
//        factory.setPort(15672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false, false,false,null);
            String message = "Hello World";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
