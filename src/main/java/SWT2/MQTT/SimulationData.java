package SWT2.MQTT;

import SWT2.controller.MqttController;
import SWT2.model.Sensor;
import SWT2.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Random;

public class SimulationData implements Runnable {


//    SENSOR REPOSITORY IST NULL KEIN AHNUNG WARUM !!
    @Autowired
    SensorRepository sRepository;

    MqttController mqttController = new MqttController();

    @Override
    public void run() {
        int id = 0;
        int inOutValue = 0;
        int lightningvalue = 50;
        int temperatureValue = 0;
        Random random;
        int randomNumber;
//        SENSOR TYPES


        String message = "{\"id\":" + id + " ,\"type\":" + 1 + ",\"value\":" + inOutValue + "}";

        while (!Thread.currentThread().isInterrupted()) {

            try {
                //IN-OUT SENSOR
              id = 1;

                  Optional<Sensor> s = sRepository.findById(id);
                  Sensor sensor = s.get();



              switch (sensor.getSensortype().getStid()){
                  case 1:
                       random = new Random();
                       randomNumber = random.nextInt(3) - 1;
                      message = "{\"id\":" + id + " ,\"type\":" + 1 + ",\"value\":" + randomNumber + "}";
                      mqttController.index(message);
                      break;
                  case 2:

                      // TEMEPRATURE SENSOR
                      random = new Random();
                      randomNumber = random.nextInt(4) - 2;

//                      Temprature must not be lower than 15 or higher than 25
                      temperatureValue = 15 + (int) (Math.random() * ((25 - 15) + 1));

//                      Temprature could increase by 1 or 2  or decrease by 1 or 2 in Rooms !
                      temperatureValue += randomNumber;

                      message = "{\"id\":" + id + " ,\"type\":" + 2 + ",\"value\":" + temperatureValue + "}";
                      mqttController.index(message);
                      break;
                  case 3:
                      // LIGHTNING SENSOR
                      random = new Random();
                      randomNumber = random.nextInt(15) - 5;

//                    Light of a room increase up to 10 and decrease by up to 5
                      lightningvalue += randomNumber;
                      message = "{\"id\":" + id + " ,\"type\":" + 3 + ",\"value\":" + lightningvalue + "}";
                      mqttController.index(message);
                      break;
              }

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (Exception ee){
                System.out.println(" this  RANDOM SENSOR ID doesnt EXIST ");
                Thread.currentThread().interrupt();
            }


        }
    }
}
