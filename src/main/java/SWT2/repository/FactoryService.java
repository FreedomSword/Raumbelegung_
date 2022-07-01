package SWT2.repository;

import SWT2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FactoryService {

    @Autowired
    Repo repo;


    public void save(Object obj){
        System.out.println(obj.getClass().getSimpleName());
        switch (obj.getClass().getSimpleName()){
            case "Room":
                repo.saveRoom((Room)obj);
                break;
            case "Building":
                repo.saveBuilding((Building)obj);
                break;
            case "Resevation":
                repo.saveReservations((Reservation) obj);
                break;
            case "User":
                repo.saveUser((User)obj);
                break;
            case "Sensor":
                repo.saveSensor((Sensor)obj);
                break;

        }
    }
}
