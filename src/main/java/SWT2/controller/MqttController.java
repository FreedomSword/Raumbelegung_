package SWT2.controller;


import SWT2.MQTT.MQTTPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    public static String Topic = "myTopic";

    @Autowired
    MQTTPublisher publisher = new MQTTPublisher();

    /**
     * @param data
     * @return
     */
    @RequestMapping(value = "myTopic", method = RequestMethod.POST)
    public String index(@RequestBody String data) {
        publisher.publishMessage("myTopic", data);
        return "Success";
    }

}
