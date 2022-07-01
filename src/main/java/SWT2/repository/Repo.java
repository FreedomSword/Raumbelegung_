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
    public Building saveBuilding(Building b){return bRepository.save(b);}
    public Building findBuildingById(int id ){return bRepository.findById(id).get();}
    public  Building getBuildingById(int id ){return bRepository.getById(id);}
    public void deleteBuildingById(int id ){ bRepository.deleteById(id);}

    //Room Repo
    public List<Room> findAllRooms(){return rRepository.findAll();}
    public Room saveRoom ( Room r){return rRepository.save(r);}
    public List<Room> findRooms(int id){return rRepository.findRooms(id);}
    public Room getRoomById(int id){return rRepository.getById(id);}
    public Room findRoomById(int id ){return rRepository.findById(id).get();}
    public void deleteRoomById(int id ){ rRepository.deleteById(id);}

    //Reservation Repo
    public List<Reservation> findAllReservations(){return resRepository.findAll();}
    public Reservation saveReservations( Reservation res){return resRepository.save(res);}
    public List<Reservation> findReservations(int roomId){return resRepository.findAllReservations(roomId);}
    public List<Reservation> findReservationsByDate(String date,int roomId){return resRepository.findAllByDate(date,roomId);}
    public Reservation findReservationById(int id ){return resRepository.findById(id).get();}
    public void deleteReservationById(int id ){ resRepository.deleteById(id);}
    public List<Reservation> findReservationsByUserId(int userId ){return resRepository.findAllByUser(userId);}

    // Sensor Repo
    public List<Sensor> findAllSensors(){return sRepository.findAll();}
    public Sensor saveSensor( Sensor s){
        return sRepository.save(s);
    }
    public List<Sensor> findAllRoomSensors(int roomId){return sRepository.findAllSensors(roomId);}
    public void deleteSensorById(int id ){ sRepository.deleteById(id);}
    public Sensor findSensorById(int id ){return sRepository.findById(id).get();}

    // SensorType Repo
    public List<Sensortype> findAllSensorsTypes(){return stRepository.findAll();}
//    public Sensortype saveSensorType( Sensortype st){return stRepository.save(st);}

    //  USER Repo
//    public List<User> findAllUsers(){return uRepository.findAll();}
    public User saveUser(User u){
        return uRepository.save(u);
    }
    public  User getUserByUsername(String username ){return uRepository.getUsersByUsername(username);}
    public User findUserById(int id ){return uRepository.findById(id).get();}

    //role repo
    public Role findRoleByName(String name){return roleRepository.findByName(name);}




}
