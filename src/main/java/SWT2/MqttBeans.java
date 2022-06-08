package SWT2;

import SWT2.dao.*;
import SWT2.model.Raum;
import SWT2.model.Sensor;
import SWT2.repository.RaumRepository;
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
import org.springframework.messaging.Message;
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
            private SensorDAO sDAO;

            @Autowired
            private RaumDAO rDAO;

            @Autowired
                    private RaumRepository rRepo;

            @Autowired
            private SensorRepository sRepo;
            Nachricht n  = new Nachricht();
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();

                try {

                    if (topic.equals("myTopic")) {
                        // System.out.println("This is our topic");
                    }

                    //Daten der Sensoren in String umwandeln
                    String msg = message.getPayload().toString();

                    //Daten der Sensoren trennen
                    String[] payload = new String[3];
                    payload = msg.split(",");

                    //Daten der Sensoren als einzelne Strings speichern
                    for (int i = 0; i < payload.length; i++) {
                        payload[i] = n.nachrichtUmwandeln(payload[i]);
                    }

                    System.out.println("Sensor ID: " + payload[0]);
                    System.out.println("Sensor Typ: " + payload[1]);
                    System.out.println("Sensor Value: " + payload[2]);


                    //payload[0] = id
                    //payload[1] = Typ
                    //payload[2] = eingabe


                    System.out.println("Länge Arry: " + payload.length);
                    if (Integer.parseInt(payload[1]) == 0) {
                        //Bewegungssensor
                        System.out.println("Case 0: Bewegungsssensor erkannt");
                        Optional<Sensor> sOp = sRepo.findById(Integer.parseInt(payload[0]));
                        Sensor s = sOp.get();
                        System.out.println(s);
                        Raum r = s.getRaum();
                        System.out.println(r.toString());
                        if (Integer.parseInt(payload[2]) == 0) {
                            r.setAkt_belegung(r.getAkt_belegung() - 1);
                            System.out.println("Aktuelle Belegung um 1 verringert");
                        } else {
                            r.setAkt_belegung(r.getAkt_belegung() + 1);
                            System.out.println("Aktuelle Belegung um 1 erhöht");
                        }

                        rRepo.save(r);


                    } else {
                        //Temperatursensor


                        //Lichtsensor


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
