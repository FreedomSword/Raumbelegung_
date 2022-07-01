package SWT2.controller;

import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.model.Sensor;
import SWT2.model.Sensortype;
import SWT2.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
///Bla
@RestController
public class SensorController {
    @Autowired
    Repo repo;
    /////////////////TABLE VIEWS/////////////////
    @Autowired
    FactoryService fs;


    //Show Sensors in Explicit Rooms

    /////////////////ADDING/////////////////

    //Save sensor to the database
    @PostMapping("/saveSensor")
    public RedirectView saveSensor(@ModelAttribute Sensor sensor, @RequestParam int roomId) {
        sensor.setRoom((repo.findRoomById(roomId)));
        fs.save(sensor);
        return new RedirectView("/roomDetails?roomId=" + roomId);
    }

    //Show sensor add form by klicking on "Add Sensor" button at rooms view without needed to insert room
    @GetMapping("/addSensorForm")
    public ModelAndView showSensorAddForm(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("addSensorInRoomForm");
        Sensor newSensor = new Sensor();
        mav.addObject("sensor", newSensor);
        Room room = repo.findRoomById(roomId);
        mav.addObject("room", room);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);


        return mav;
    }

    /////////////////DELETE AND UPDATE/////////////////

    //Delete the Sensor from database
    @GetMapping("/deleteSensor")
    public RedirectView deleteSensor(@RequestParam int sensorId) {
        Room room = repo.getRoomById(repo.findSensorById(sensorId).getRoom().getRid());

        repo.deleteSensorById(sensorId);
        return new RedirectView("/roomDetails?roomId="+room.getRid());
    }

    //Show sensor update form
    @GetMapping("/SensorUpdateForm")
    public ModelAndView showSensorUpdateForm(@RequestParam int sensorId) {
        ModelAndView mav = new ModelAndView("addSensorInRoomForm");
        Sensor sensor = repo.findSensorById(sensorId);
        Room room = repo.findRoomById(sensor.getRoom().getRid());
        mav.addObject("room", room);
        mav.addObject("sensor", sensor);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);

        return mav;
    }

    /////////////////MODEL ATTRIBUTES/////////////////

    @ModelAttribute("room")
    public List<Room> populateList(@NotNull Model model) {
        List<Room> list = repo.findAllRooms();
        model.addAttribute("raueme", list);
        return  list;
    }

    @ModelAttribute("sensortype")
    public List<Sensortype> populateListSensortype(Model model) {
        List <Sensortype> list = repo.findAllSensorsTypes();
        model.addAttribute("sensortype", list);
        return  list;
    }

}

