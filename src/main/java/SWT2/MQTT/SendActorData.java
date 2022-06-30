/*
package SWT2.MQTT;

import SWT2.SpringContext;
import SWT2.controller.MqttController;
import SWT2.model.Room;
import SWT2.repository.RoomRepository;
import SWT2.repository.SensorRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SendActorData implements Runnable {

    private SensorRepository getSensorRepo() {

        return SpringContext.getBean(SensorRepository.class);
    }

    private RoomRepository getRoomRepo() {
        return SpringContext.getBean(RoomRepository.class);
    }

    MqttController mqttController = new MqttController();

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void run() {
        int id = 0;

        String message;
        while (!Thread.currentThread().isInterrupted()) {

            try {
                RoomRepository rRepository = getRoomRepo();

                List<Room> rooms = rRepository.findAll();

                for(int i = 0; i < rooms.size(); i++) {
                    Room room = rooms.get(i);
                    int output = 0;
                    String topic ="";

                    if(room.getCurrentTemperature() > room.getTargetTemperature()) {
                        output = room.getTargetTemperature() - room.getCurrentTemperature();
                    }
                    else if(room.getTargetTemperature() > room.getCurrentTemperature()) {
                        output = room.getTargetTemperature() - room.getCurrentTemperature();
                    }
                    topic = room.getName() + "_2";
                    message = "{\"value\":" + output + "}";
                    mqttController.index(topic, message);
                    logger.info("Nachricht mir Topic: " + topic + " gesendet. Output = " + output);


                    if(room.getCurrentLightLevel() < room.getTargetLightLevel()) {
                        output = room.getTargetLightLevel() - room.getCurrentLightLevel();
                    }

                    else if(room.getTargetLightLevel() < room.getCurrentLightLevel()) {
                        output = 0;
                    }
                    topic = room.getName() + "_3";
                    message = "{\"value\":" + output + "}";
                    mqttController.index(topic, message);
                    logger.info("Nachricht mir Topic: " + topic + " gesendet. Output = " + output);

                }
                Thread.sleep(4000);
            }

            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public int getSensorType(int id) throws NoSuchElementException {
        try {
            return getSensorRepo()
                    .findById(id)
                    .get()
                    .getSensortype()
                    .getStid();
        }
        catch (NoSuchElementException noSuchElementException) {
            logger.warning("SensorID: " + id  + " Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;

        }
    }

    public int getCurrentTemperature(int id) throws NoSuchElementException {
        try {
            return getSensorRepo()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCurrentTemperature();
        }
        catch (NoSuchElementException noSuchElementException) {
            logger.warning("SensorID: " + id  + " Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }

    public int getCurrenLightLevel(int id) throws NoSuchElementException {
        try {
            return getSensorRepo()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCurrentLightLevel();
        } catch (NoSuchElementException noSuchElementException) {
            logger.warning("SensorID: " + id  + " Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }

    public int getCurrenOccupacity(int id) throws NoSuchElementException {
        try {
            return getSensorRepo()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCur_occupancy();
        } catch (NoSuchElementException noSuchElementException) {
            logger.warning("SensorID: " + id  + " Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }

    public int getMaxOccupacity(int id) throws NoSuchElementException {
        try {
            return getSensorRepo()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getMax_occupancy();
        } catch (NoSuchElementException noSuchElementException) {
            logger.warning("SensorID: " + id  + " Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }
}
*/
