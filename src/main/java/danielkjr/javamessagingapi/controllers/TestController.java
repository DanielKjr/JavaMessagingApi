package danielkjr.javamessagingapi.controllers;

import com.rabbitmq.stream.impl.Client;
import danielkjr.javamessagingapi.MessageBroker.MessageProducer;
import danielkjr.javamessagingapi.MessageBroker.Receive;
import danielkjr.javamessagingapi.MessageBroker.Send;
import danielkjr.javamessagingapi.MessageBroker.dbOperationMessage;
import danielkjr.javamessagingapi.Model.MQLogDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    private MessageProducer messageProducer;

    public TestController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/something")
    @ResponseBody
    public void hello(){
//        Send.send();
        dbOperationMessage message = new dbOperationMessage();
        message.setBroker("TestController");
        MQLogDto log = new MQLogDto();
        log.message = "Something";
        message.setOperation("insert");
        message.setData(log);
        messageProducer.sendMessage(message);

    }


    @GetMapping("/something1")
    @ResponseBody
    public void rec(){
        Receive.receive();

    }
}
