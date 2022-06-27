package SWT2;


import SWT2.MQTT.SimulationData;
import SWT2.controller.MqttController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Swt2RaumbelegungNeuApplication {


	public static void main(String[] args) {
		SpringApplication.run(Swt2RaumbelegungNeuApplication.class, args);
	SimulationData sm = new SimulationData();
		Thread thread = new Thread (sm);
		thread.start();
	}

}
