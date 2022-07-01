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

		MqttController mqttController = new MqttController();

		/*startSimulation(mqttController);
		startActors(mqttController);*/

	}

	public static void startSimulation(MqttController mqttController) {
		SimulationData sd = new SimulationData(mqttController);
		Thread thread = new Thread (sd);
		thread.start();
	}

	public static void startActors(MqttController mqttController) {
		SendActorData sad = new SendActorData(mqttController);
		Thread thread2 = new Thread(sad);
		thread2.start();
	}
}
