package SWT2.repository;

import SWT2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class Repo  {

    @Autowired
    private BuildingRepository bRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private RoomRepository rRepository;

    @Autowired
    private  ReservationRepository resRepository;

    @Autowired
    private SensorRepository sRepository;

    @Autowired
    private SensortypeRepository stRepository;

    @Autowired
    private RoleRepository roleRepository;





// Building Repo
    public List<Building> findAllBuildings(){
        return bRepository.findAll();
    }
    public Building saveBuilding(Building b){
        return bRepository.save(b);
    }
    public Building findById (int id ){return bRepository.findById(id).get();}
    public  Building getById(int id ){return bRepository.getById(id);}
    public void deleteById(int id ){ bRepository.deleteById(id);}

    //Room Repo
    public List<Room> findAllRooms(){return rRepository.findAll();}
    public Room saveRoom ( Room r){
        return rRepository.save(r);
    }
    public List<Room> findRoom (int id){return rRepository.findRoom(id);}


//  USER Repo
    public List<User> findAllUsers(){return uRepository.findAll();}
    public User saveUser ( User u){
        return uRepository.save(u);
    }
    public  User getUserByUserName(String username ){return uRepository.getUsersByUsername(username);}


// Sensor Repo
    public List<Sensor> findAllSensors(){return sRepository.findAll();}
    public Sensor saveSensor( Sensor s){
        return sRepository.save(s);
    }


    // SensorType Repo
    public List<Sensortype> findAllSensorsTypes(){return stRepository.findAll();}
    public Sensortype saveSensorType( Sensortype st){return stRepository.save(st);}

    //Reservation Repo
    public List<Reservation> findAllReservations(){return resRepository.findAll();}
    public Reservation saveReservations( Reservation res){return resRepository.save(res);}

    //role repo
    public List<Role> findAllRoles(){return roleRepository.findAll();}
    public Role saveReservations( Role role){return roleRepository.save(role);}



}
