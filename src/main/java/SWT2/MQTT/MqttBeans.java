package SWT2.MQTT;


import SWT2.model.Room;
import SWT2.model.Sensor;
import SWT2.repository.RoomRepository;
import SWT2.repository.SensorRepository;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.Optional;


@Configuration
public class MqttBeans {
    @Value("${MQTT.USER}")
    private String userName;
    @Value("${MQTT.PASSWORD}")
    private String password;
    @Value("${MQTT.URL}")
    private String url;



    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] {url});
        options.setUserName(userName);

        options.setPassword(password.toCharArray());
        options.setCleanSession(true);

        factory.setConnectionOptions(options);

        return factory;

    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverIn", mqttPahoClientFactory(), "#");

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Autowired
            private RoomRepository rRepo;

            @Autowired
            private SensorRepository sRepo;
            Message n  = new Message();


            @Override
            public void handleMessage(org.springframework.messaging.Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();

                    //Convert sensor data in string
                    String msg = message.getPayload().toString();

                    //Separate individual data
                    String[] payload = new String[3];
                    payload = msg.split(",");

                    //Save data as individual strings
                    for (int i = 0; i < payload.length; i++) {
                        payload[i] = n.convertMessage(payload[i]);
                    }
                    System.out.println("");
                    System.out.println("------------------------");
                    System.out.println("NEUE SENSORDATEN:");
                    System.out.println("Sensor ID: " + payload[0]);
                    System.out.println("Sensor Typ: " + payload[1]);
                    System.out.println("Sensor Value: " + payload[2]);
                    System.out.println("");


try {
    Room room = sRepo.findById(Integer.parseInt(payload[0])).get().getRoom();
    System.out.println(room.getName());
    switch (Integer.parseInt(payload[1])) {
        case 1:
            System.out.println("Case 1: Bewegungsssensor erkannt");

            if(Integer.parseInt(payload[2]) == 0) {
                room.setCur_occupancy(room.getCur_occupancy()-1);
            }
            else if (Integer.parseInt(payload[2]) == 1) {
                room.setCur_occupancy(room.getCur_occupancy()+1);
            }
            else {

            }
            rRepo.save(room);
            break;

        case 2:
            room.setCurrentTemperature(Integer.parseInt(payload[2]));
            System.out.println("Case 2: Temperatur erkannt ");
            rRepo.save(room);
            break;

        case 3:
            room.setCurrentLightLevel(Integer.parseInt(payload[2]));
            System.out.println("Case 3: Lightning erkannt");
            rRepo.save(room);
            break;

    }
}catch (Exception e){

}
            }
        };


    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOut", mqttPahoClientFactory());

        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("'");
        return messageHandler;
    }
}