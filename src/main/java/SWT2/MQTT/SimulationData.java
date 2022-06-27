package SWT2.MQTT;

import SWT2.controller.MqttController;
import SWT2.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class SimulationData implements Runnable {

    MqttController mqttController = new MqttController();

    @Override
    public void run() {
        int id = 0;
        int inOutValue = 0;
        int lightningvalue = (int) (Math.random() * ((100) + 1));
        int temperatureValue = 0;

//        SENSOR TYPES


        String message1 = "{\"id\":" + id + " ,\"type\":" + 1 + ",\"value\":" + inOutValue + "}";
        String message2 = "{\"id\":" + id + " ,\"type\":" + 2 + ",\"value\":" + lightningvalue + "}";
        String message3 = "{\"id\":" + id + " ,\"type\":" + 3 + ",\"value\":" + temperatureValue + "}";


        while (true) {

            try {
                //IN-OUT SENSOR
//                id = (int) (Math.random() * (5));
                id = 1;
                Random random = new Random();
                int randomNumber = random.nextInt(3) - 1;
                inOutValue += randomNumber;
                if (inOutValue < 0) {
                    inOutValue = 1;
                }
                message1 = "{\"id\":" + id + " ,\"type\":" + 1 + ",\"value\":" + inOutValue + "}";
                mqttController.index(message1);

                //TEMPERATURE SENSOR

                id = (int) (Math.random() * (5));
                random = new Random();
                randomNumber = random.nextInt(4) - 2;

                temperatureValue = 15 + (int) (Math.random() * ((25 - 15) + 1));
                temperatureValue += randomNumber;

                message2 = "{\"id\":" + id + " ,\"type\":" + 2 + ",\"value\":" + temperatureValue + "}";
                mqttController.index(message2);


                // LIGHTNING SENSOR
                id = (int) (Math.random() * (5));
                random = new Random();
                randomNumber = random.nextInt(15) - 5;
                lightningvalue += randomNumber;
                message3 = "{\"id\":" + id + " ,\"type\":" + 3 + ",\"value\":" + lightningvalue + "}";
                mqttController.index(message3);


                Thread.sleep(2500);

            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
                e.printStackTrace();
            }

        }
    }
}
