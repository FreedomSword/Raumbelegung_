package SWT2.controller;


import SWT2.MQTT.MqttPublisher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    public static String Topic = "myTopic";

    MqttPublisher publisher = new MqttPublisher();

    @RequestMapping(method = RequestMethod.POST)
    public String index(String topic,@RequestBody String data) {
        publisher.publishMessage(topic , data);
        return "Success";
    }

}
