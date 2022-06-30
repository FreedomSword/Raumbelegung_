package SWT2;

<<<<<<< Updated upstream
=======

import SWT2.MQTT.SendActorData;
import SWT2.MQTT.SimulationData;
>>>>>>> Stashed changes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Swt2RaumbelegungNeuApplication {

	public static void main(String[] args) {
<<<<<<< Updated upstream
		SpringApplication.run(Swt2RaumbelegungNeuApplication.class, args);
=======

		ConfigurableApplicationContext appContext = SpringApplication.run(Swt2RaumbelegungNeuApplication.class, args);
		SimulationData sd = new SimulationData();
		SendActorData sad = new SendActorData();
		Thread thread = new Thread (sd);
		Thread thread2 = new Thread(sad);
		thread.start();
		//thread2.start();
>>>>>>> Stashed changes
	}

}
