package SWT2.MQTT;


import SWT2.model.Actor;
import SWT2.model.Room;
import SWT2.repository.ActorRepository;
import SWT2.repository.RoomRepository;
import SWT2.repository.SensorRepository;
import org.checkerframework.checker.units.qual.A;
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

import java.util.List;
import java.util.logging.Logger;


@Configuration
public class MqttBeans {
    @Value("${MQTT.USER}")
    private String userName;
    @Value("${MQTT.PASSWORD}")
    private String password;
    @Value("${MQTT.URL}")
    private String url;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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

            @Autowired
            private ActorRepository aRepo;
            Message m = new Message();


            @Override
            public synchronized void  handleMessage(org.springframework.messaging.Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();

                if(topic.matches("[A-Z].[A-Z].[0-9][0-9]")) {
                    //Convert sensor data in string
                    String msg = message.getPayload().toString();

                    //Separate individual data
                    String[] payload = new String[3];
                    payload = msg.split(",");

                    //Save data as individual strings
                    for (int i = 0; i < payload.length; i++) {
                        payload[i] = m.convertMessage(payload[i]);
                    }

                    logger.info("Neue Sensordaten: SensorID " + payload[0] + " SensorTyp " + payload[1] + " SensorValue " + payload[2]);


                    try {
                        Room room = sRepo.findById(Integer.parseInt(payload[0])).get().getRoom();

                        switch (Integer.parseInt(payload[1])) {
                            case 1:
                                //System.out.println("Case 1: Bewegungsssensor erkannt");

                                if (Integer.parseInt(payload[2]) == 0) {
                                    room.setCur_occupancy(room.getCur_occupancy() - 1);
                                } else if (Integer.parseInt(payload[2]) == 1) {
                                    room.setCur_occupancy(room.getCur_occupancy() + 1);
                                } else {

                                }
                                rRepo.save(room);
                                break;

                            case 2:
                                room.setCurrentTemperature(Integer.parseInt(payload[2]));
                               // System.out.println("Case 2: Temperatur erkannt ");
                                rRepo.save(room);
                                break;

                            case 3:
                                room.setCurrentLightLevel(Integer.parseInt(payload[2]));
                               //System.out.println("Case 3: Lightning erkannt");
                                rRepo.save(room);
                                break;

                        }
                    } catch (Exception e) {
                    }
                }

                else if (topic.matches("[A-Z].[A-Z].[0-9][0-9].[2-3]")) {

                    String msg = message.getPayload().toString();
                    int value = Integer.parseInt(m.convertMessage(msg));

                    String roomName = topic.substring(0, topic.length()-2);
                    int type = Integer.parseInt(topic.substring(topic.length()-1, topic.length()));

                    Room room = rRepo.findByName(roomName);
                    List<Actor> actors = aRepo.getByRoomId(room.getRid());


                    if(type == 2) {
                        room.setCurrentTemperature(room.getCurrentTemperature()+value);
                        rRepo.save(room);

                        for(int i = 0; i < actors.size(); i++) {
                            Actor a = actors.get(i);
                            if(a.getActortype().getAtid() == 5) {
                                a.setActivity(value);
                                aRepo.save(a);
                            }

                        }

                    }

                    if(type == 3) {
                        room.setCurrentLightLevel(room.getCurrentLightLevel()+value);

                        for(int i = 0; i < actors.size(); i++) {
                            Actor a = actors.get(i);
                            if(a.getActortype().getAtid() == 6) {
                                a.setActivity(value);
                                aRepo.save(a);
                            }

                        }

                    }

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

        messageHandler.setAsync(false);
        messageHandler.setDefaultTopic("");
        return messageHandler;
    }
}