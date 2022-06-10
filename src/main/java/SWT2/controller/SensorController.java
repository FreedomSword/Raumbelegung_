package SWT2.controller;

import SWT2.model.Room;
import SWT2.model.Sensor;
import SWT2.model.Sensortype;
import SWT2.repository.RoomRepository;
import SWT2.repository.SensorRepository;
import SWT2.repository.SensortypeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@RestController
public class SensorController {
    @Autowired
    private SensorRepository sRepository;

    @Autowired
    private RoomRepository rRepository;

    @Autowired
    private SensortypeRepository stRepository;

    /////////////////TABLE VIEWS/////////////////

    //Show Sensors
    @GetMapping("/showSensor")
    public ModelAndView showSensor() {
        ModelAndView mav = new ModelAndView("sensor");
        List<Sensor> list = sRepository.findAll();
        mav.addObject("sensor", list);
        return  mav;
    }

    //Show Sensors in Explicit Rooms
    @GetMapping("/sensorenFiltered")
    public ModelAndView showSensorsInRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("sensorsInRoom");
        Room r = rRepository.getById(roomId);
        List<Sensor> list = sRepository.findAllSensors(roomId);
        mav.addObject("room", r );
        mav.addObject("sensor", list);
        return  mav;
    }

    /////////////////ADDING/////////////////

    //Save sensor to the database
    @PostMapping("/saveSensor")
    public RedirectView saveSensor(@ModelAttribute Sensor sensor, @RequestParam int roomId) {
        sensor.setRoom((rRepository.findById(roomId)).get());
        sRepository.save(sensor);
        return new RedirectView("/showSensor");
    }

    //Show sensor add form by klicking on "Add Sensor" button at rooms view without needed to insert room
    @GetMapping("/addSensorForm")
    public ModelAndView showSensorAddForm(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("addSensorInRoomForm");
        Sensor newSensor = new Sensor();
        mav.addObject("sensor", newSensor);
        Room room = rRepository.findById(roomId).get();
        mav.addObject("room", room);
        return mav;
    }

    /////////////////DELETE AND UPDATE/////////////////

    //Delete the Sensor from database
    @GetMapping("/deleteSensor")
    public RedirectView deleteSensor(@RequestParam int sensorId) {
        sRepository.deleteById(sensorId);
        return new RedirectView("/showSensor");
    }

    //Show sensor update form
    @GetMapping("/SensorUpdateForm")
    public ModelAndView showSensorUpdateForm(@RequestParam int sensorId) {
        ModelAndView mav = new ModelAndView("addSensorInRoomForm");
        Sensor sensor = sRepository.findById(sensorId).get();
        mav.addObject("sensor", sensor);
        return mav;
    }

    /////////////////MODEL ATTRIBUTES/////////////////

    @ModelAttribute("room")
    public List<Room> populateList(@NotNull Model model) {
        List<Room> list = rRepository.findAll();
        model.addAttribute("raueme", list);
        return  list;
    }

    @ModelAttribute("sensortype")
    public List<Sensortype> populateListSensortype(Model model) {
        List <Sensortype> list = stRepository.findAll();
        model.addAttribute("sensortype", list);
        return  list;
    }

}

