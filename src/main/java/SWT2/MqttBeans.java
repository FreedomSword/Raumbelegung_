package SWT2;

import SWT2.dao.*;
import SWT2.model.Raum;
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

            SensorDAOImpl s = new SensorDAOImpl();

            @Autowired
            private RaumDAO rDAO;
            Nachricht n  = new Nachricht();
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                if(topic.equals("myTopic")) {
                   // System.out.println("This is our topic");
                }

                //Daten der Sensoren in String umwandeln
                String msg =   message.getPayload().toString();

                //Daten der Sensoren trennen
                String[] payload =msg.split(",");

                //Daten der Sensoren als einzelne Strings speichern
                for(int i = 0; i < payload.length; i++) {
                    payload[i] = n.nachrichtUmwandeln(payload[i]);
                }

                //payload[0] = id
                //payload[1] = art
                //payload[2] = eingabe

              //  Raum r = rDAO.getById(s.getById(Integer.parseInt(payload[0])).getRaumID());

           //     rDAO.updateBelegung(Raum r, s.getById(Integer.parseInt(payload[0])).getRaumID());

                for(String a:payload) System.out.println(a);


            }
        };


    }

/*
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler addActivity() {
        return new MessageHandler() {

            GebauedeDAOImpl gdao = new GebauedeDAOImpl();
            SensorDAO sdao = new SensorDAOImpl();

            String[] payload = new String[3];

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                if (topic.equals("myTopic")) {
                    gdao.getById(1);

                }

            }
        };

    }*/
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
