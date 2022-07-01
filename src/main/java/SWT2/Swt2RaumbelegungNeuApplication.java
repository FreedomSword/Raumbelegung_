package SWT2;


import SWT2.MQTT.SendActorData;
import SWT2.MQTT.SimulationData;
import SWT2.controller.MqttController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootApplication
public class Swt2RaumbelegungNeuApplication {


	public static void main(String[] args) {

		ConfigurableApplicationContext appContext = SpringApplication.run(Swt2RaumbelegungNeuApplication.class, args);
<<<<<<< Updated upstream
//		SimulationData sd = new SimulationData();
//		Thread thread = new Thread (sd);
//		thread.start();
//
//		SendActorData sad = new SendActorData();
//		Thread thread2 = new Thread(sad);
//		thread2.start();
=======

		MqttController mqttController = new MqttController();
		SimulationData sd = new SimulationData(mqttController);
		Thread thread = new Thread (sd);
		thread.start();

		SendActorData sad = new SendActorData(mqttController);
		Thread thread2 = new Thread(sad);
		thread2.start();
>>>>>>> Stashed changes
	}
}
