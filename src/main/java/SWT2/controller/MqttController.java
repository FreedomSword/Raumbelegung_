package SWT2.controller;


import SWT2.MQTT.MqttPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {
    public static String Topic = "myTopic";

    @Autowired
    MqttPublisher publisher = new MqttPublisher();

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
