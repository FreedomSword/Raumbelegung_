package SWT2.MQTT;

import SWT2.controller.MqttController;
import org.springframework.beans.factory.annotation.Autowired;

public class SimulationData implements Runnable
{

    MqttController mqttController = new MqttController();

    @Override
    public void run() {
        int id = 0;
         int type = 0;
        int value = 0;
String message = "{\"id\":"+id+" ,\"type\":"+type+ ",\"value\":"+value+ "}";

        while (true) {

            try {
              id = (int)(Math.random() * ((100)));
                type = /* (int)(Math.random() * ((2- 0)));*/  0;
                value =  (int)(Math.random() * ((1)));
                message = "{\"id\":"+id+" ,\"type\":"+type+ ",\"value\":"+value+ "}";
                Thread.sleep(3000);
                mqttController.index(message);
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
                e.printStackTrace();
            }

        }
    }


}
