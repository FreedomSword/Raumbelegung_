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

                try {


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


                    //payload[0] = id
                    //payload[1] = type
                    //payload[2] = insert value


                    //Determination of sensor type

                    //1 = Motion sensor
                    if (Integer.parseInt(payload[1]) == 1) {
                        System.out.println("Case 0: Bewegungsssensor erkannt");
                        Optional<Sensor> sOp = sRepo.findById(Integer.parseInt(payload[0]));
                        Sensor s = sOp.get();
                        Room r = s.getRoom();

                        //Determination of the insert value

                        //0 = Going out
                        if (Integer.parseInt(payload[2]) == 0) {

                            //Current occupancy = 0?
                            if(r.getCur_occupancy() == 0) {

                                //Do nothing (Due to the simulation of the data, incorrect entries can logically occur, which are intercepted here)
                                System.out.println("Aktuelle Belegung = 0! Deshalb wurden die Daten verworfen");
                            }

                            else {

                                //Current occupancy - 1
                                r.setCur_occupancy(r.getCur_occupancy() - 1);
                                System.out.println("Aktuelle Belegung um 1 verringert");
                                System.out.println("Alte Belegung: " + (r.getCur_occupancy() + 1) + " Neue Belegung: " + r.getCur_occupancy());
                            }

                            //1 = Going in
                        } else {
                            r.setCur_occupancy(r.getCur_occupancy() + 1);
                            System.out.println("Aktuelle Belegung um 1 erhoeht");
                            System.out.println("Aktuelle Belegung um 1 verringert");
                            System.out.println("Alte Belegung: " + (r.getCur_occupancy()-1) + " Neue Belegung: " + r.getCur_occupancy());

                        }

                        rRepo.save(r);


                        //Temperature sensor

                        //Light sensor


                    }
                }
                catch(Exception e) {

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