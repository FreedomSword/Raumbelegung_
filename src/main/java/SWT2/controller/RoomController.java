package SWT2.controller;

import SWT2.MQTT.SimulationData;
import SWT2.model.*;
import SWT2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RoomController {


    @Autowired
    Repo repo;

    @Autowired
    FactoryService fs;

    MqttController mqttController = new MqttController();
    SimulationData simulationData = new SimulationData(mqttController);

    /////////////////TABLE VIEWS/////////////////



    /////////////////ADDING/////////////////

    //Save the Room to the Database

    @PostMapping("/saveRoomInBuilding")
    public RedirectView saveRoom(@ModelAttribute Room room, @RequestParam int buildingId, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        room.setPhoto(fileName);

        String uploadDir = "room-photos/" + room.getName();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Building building = repo.findBuildingById(buildingId);
        room.setBuilding(building);
        room.setCurrentLightLevel(10);
        room.setCurrentTemperature(20);
        fs.save(room);
        return new RedirectView("/roomDetails?roomId=" + room.getRid() );
    }

    @PostMapping("/updateSaveRoomInBuilding")
    public RedirectView UpdatesaveRoom(@ModelAttribute Room room, @RequestParam int buildingId) {

        Room currRoom = repo.getRoomById(room.getRid());
        String image = currRoom.getPhoto();

        Building building = repo.findBuildingById(buildingId);
        room.setBuilding(building);
        room.setPhoto(image);
        fs.save(room);
        return new RedirectView("/roomDetails?roomId=" + room.getRid() );
    }

    @GetMapping("/roomDetails")
    public ModelAndView showSensorListOfRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomDetails");
        Room room = repo.findRoomById(roomId);


        mav.addObject("sm", simulationData);
        User user = repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);

        String date =  LocalDate.now().toString();

        List<Reservation> reservations = repo.findReservationsByDate(date, roomId);
        mav.addObject("reservations", reservations);
        List<Sensor> list = repo.findAllRoomSensors(roomId);
        mav.addObject("room", room );
        mav.addObject("sensor", list);
        return  mav;
    }

    //Show room add form by klicking on "Add Room" button in buildings view without needed to insert building
    @GetMapping("/addRoomInBuildingForm")
    public ModelAndView showRoomAddForm(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("addRoomInBuildingForm");
        Room room = new Room();
        mav.addObject("room", room);
        Building building = repo.findBuildingById(buildingId);
        mav.addObject("building", building);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);

        return mav;
    }

    /////////////////DELETE AND UPDATE/////////////////

    //Delete the Room from the Database
    @GetMapping("/deleteRoom")
    public RedirectView deleteRaum(@RequestParam int roomId) {
        Building building = repo.findBuildingById(repo.findRoomById(roomId).getBuilding().getBid());
        repo.deleteRoomById(roomId);
        return new RedirectView("/buildingDetails?buildingId=" + building.getBid());
    }

    //Show room update form
    @GetMapping("/UpdateRoomForm")
    public ModelAndView updateValuesInRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("UpdateValuesInRoomForm");
        Room room = repo.findRoomById(roomId);
        Building building = repo.findBuildingById(room.getBuilding().getBid());
        mav.addObject("building", building);
        mav.addObject("room", room);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);
        return mav;
    }

    @GetMapping("/updateRoomInBuilding")
    public ModelAndView updateRoom(@RequestParam int roomId,@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("UpdateRoomForm");
        Room room = repo.getRoomById(roomId);
        Building building = repo.getBuildingById(buildingId);

        mav.addObject("room", room);
        mav.addObject("building", building);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);
        return mav;
    }

    /////////////////MODEL ATTRIBUTES/////////////////

    @ModelAttribute("building")
    public List<Building> populateList(Model model) {
        List <Building> buildingList = repo.findAllBuildings();
        model.addAttribute("building", buildingList);
        return  buildingList;
    }





}

