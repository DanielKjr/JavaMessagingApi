package danielkjr.javamessagingapi.controllers;

import danielkjr.javamessagingapi.MessageBroker.Receive;
import danielkjr.javamessagingapi.MessageBroker.Send;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/something")
    @ResponseBody
    public String hello(){
        Send.send();
        return "Ayo";
    }


    @GetMapping("/something1")
    @ResponseBody
    public void rec(){
        Receive.receive();

    }
}
