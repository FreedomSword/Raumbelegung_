package SWT2;


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
		SimulationData sd = new SimulationData();
		Thread thread = new Thread (sd);
		thread.start();
	}
}
