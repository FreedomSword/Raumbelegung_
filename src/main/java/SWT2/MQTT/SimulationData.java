package SWT2.MQTT;

import SWT2.SpringContext;
import SWT2.controller.MqttController;
import SWT2.repository.SensorRepository;


import java.util.NoSuchElementException;
import java.util.Random;


public class SimulationData implements Runnable {


//    SENSOR REPOSITORY IST NULL KEIN AHNUNG WARUM !!
   private SensorRepository getSensor() {
       return SpringContext.getBean(SensorRepository.class);
   }

    MqttController mqttController = new MqttController();

    @Override
    public void run() {
        int id = 0;

        String message;
        while (!Thread.currentThread().isInterrupted()) {


                try {
                    for(int i = 0; i < 3; i++){
                    int min = 0;
                    int max = 100;
                    //Random generate Sensor ID between MIN and MAX.

                    id = (int) Math.random() * (max - min) + min;

                    //Get Type of Sensor with id ID.

                    int sensorType = getSensorType(id);


                    switch (sensorType) {

                        //Motion Sensor
                        case 1:
                            final int[] roomArray = new int[]{0, 1};
                            Random roomPick = new Random();
                            int currenOccupacity = getCurrenOccupacity(id);
                            int output = 0;

                            //We make sure that the occupancy cannot be under or overcut

                            if (currenOccupacity < 0 && currenOccupacity < getMaxOccupacity(id)) {
                                output = roomPick.nextInt(roomArray.length);
                            } else if (currenOccupacity == 0) {
                                output = 1;
                            } else if (currenOccupacity == getMaxOccupacity(id)) {
                                output = 0;
                            }

                            message = "{\"id\":" + id + " ,\"type\":" + 1 + ",\"value\":" + output + "}";
                            mqttController.index(message);
                            break;


                        //Temperature Sensor
                        case 2:
                            final int[] arr = new int[]{-1, 0, 1};
                            Random arrayPick = new Random();
                            int temperatureValue = getCurrentTemperature(id);

                            //Increase or Decrease the Temperature randomly at 1 depending on current RoomTemperature
                            if (temperatureValue > 18 && temperatureValue < 30) {
                                temperatureValue = getCurrentTemperature(id) + (arrayPick.nextInt(arr.length));
                            }

                            //We assume that the Temperature Minimum is 18° C
                            else if (temperatureValue == 18) {
                                temperatureValue = 19;
                            }
                            //We assume taht the Temperature Maximum is 30° C
                            else if (temperatureValue == 30) {
                                temperatureValue = 29;
                            }


                            message = "{\"id\":" + id + " ,\"type\":" + 2 + ",\"value\":" + temperatureValue + "}";
                            mqttController.index(message);
                            break;


                        //Light Sensor
                        //The Light is divided into 20 Levels
                        case 3:
                            final int[] lightArray = new int[]{-3, -2, -1, 0, 1, 2, 3};
                            Random arrayPickLight = new Random();
                            int lightningvalue = getCurrenLightLevel(id);

                            //Increase or Decrease the Temperature randomly at 1 depending on current RoomTemperature
                            if (lightningvalue > 0 && lightningvalue < 20) {
                                lightningvalue = getCurrenLightLevel(id) + (arrayPickLight.nextInt(lightArray.length));
                            }

                            //We assume that the Temperature Minimum is 18° C
                            else if (lightningvalue == 0) {
                                lightningvalue = 2;
                            }
                            //We assume taht the Temperature Maximum is 30° C
                            else if (lightningvalue == 30) {
                                lightningvalue = 29;
                            }

                            message = "{\"id\":" + id + " ,\"type\":" + 3 + ",\"value\":" + lightningvalue + "}";
                            mqttController.index(message);
                            break;
                    }
                }
                    Thread.sleep(5000);


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.println(" this  RANDOM SENSOR ID doesnt EXIST ");
                    Thread.currentThread().interrupt();
                }


        }
    }

    public int getSensorType(int id) throws NoSuchElementException {
        try {
            return getSensor()
                    .findById(id)
                    .get()
                    .getSensortype()
                    .getStid();
        }
        catch (NoSuchElementException noSuchElementException) {
            System.out.println("Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;

        }

    }

    public int getCurrentTemperature(int id) throws NoSuchElementException {
        try {
            return getSensor()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCurrentTemperature();
        }
        catch (NoSuchElementException noSuchElementException) {
            System.out.println("Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }

    public int getCurrenLightLevel(int id) throws NoSuchElementException {
        try {
            return getSensor()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCurrentLightLevel();
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }
    }

    public int getCurrenOccupacity(int id) throws NoSuchElementException {
        try {
            return getSensor()
                    .findById(id)
                    .get()
                    .getRoom()
                    .getCur_occupancy();
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Kein Element mit dieser ID gefunden Simulation verworfen");
            return -1;
        }

    }

        public int getMaxOccupacity(int id) throws NoSuchElementException {
            try {
                return getSensor()
                        .findById(id)
                        .get()
                        .getRoom()
                        .getMax_occupancy();
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Kein Element mit dieser ID gefunden Simulation verworfen");
                return -1;
            }
    }
}
