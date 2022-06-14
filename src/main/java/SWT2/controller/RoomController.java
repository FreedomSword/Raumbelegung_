package SWT2.controller;

import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.model.Sensor;
import SWT2.repository.BuildingRepository;
import SWT2.repository.RoomRepository;
import SWT2.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository rRepository;

    @Autowired
    private BuildingRepository bRepository;

    @Autowired
    private SensorRepository sRepository;

    /////////////////TABLE VIEWS/////////////////

    //Show Rooms
    @GetMapping("/showRoom")
    public ModelAndView showRoomList() {
        ModelAndView mav = new ModelAndView("room");
        List<Room> list = rRepository.findAll();
        mav.addObject("room", list);
        return  mav;
    }



    /////////////////ADDING/////////////////

    //Save the Room to the Database
    @PostMapping("/saveRoom")
    public RedirectView saveRoom(@ModelAttribute Room room) {

        Optional<Building> buildingOptional = bRepository.findById(room.getBuilding().getBid());
        Building building = buildingOptional.get();
        room.setBuilding(building);
        rRepository.save(room);
        return new RedirectView("/buildingDetails(buildingId=${building.bid})");
    }

    @PostMapping("/saveRoomInBuilding")
    public RedirectView saveRoom(@ModelAttribute Room room, @RequestParam int buildingId) {
        Building building = (bRepository.findById(buildingId)).get();
        room.setBuilding(building);
        rRepository.save(room);
        return new RedirectView("/buildingDetails?buildingId=" + buildingId );
    }
    @GetMapping("/roomDetails")
    public ModelAndView showSensorListOfRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomDetails");
        Room room = rRepository.findById(roomId).get();
        List<Sensor> list = sRepository.findAllSensors(roomId);
        mav.addObject("room", room );
        mav.addObject("sensor", list);
        return  mav;
    }



    //Show room add form by klicking on "Add Room" button in buildings view without needed to insert building
    @GetMapping("/AddRoomInBuildingForm")
    public ModelAndView showRoomAddForm(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("addRoomInBuildingForm");
        Room room = new Room();
        mav.addObject("room", room);
        Building building = bRepository.findById(buildingId).get();
        mav.addObject("building", building);
        return mav;
    }
    /////////////////DELETE AND UPDATE/////////////////

    //Delete the Room from the Database
    @GetMapping("/deleteRoom")
    public RedirectView deleteRaum(@RequestParam int roomId) {
        Building building = bRepository.findById(rRepository.findById(roomId).get().getBuilding().getBid()).get();
        rRepository.deleteById(roomId);
        return new RedirectView("/buildingDetails?buildingId=" + building.getBid());
    }

    //Show room update form
    @GetMapping("/UpdateRoomForm")
    public ModelAndView showRoomUpdateForm(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("addRoomInBuildingForm");
        Room room = rRepository.findById(roomId).get();
        Building building = bRepository.findById(room.getBuilding().getBid()).get();
        mav.addObject("building", building);
        mav.addObject("room", room);
        return mav;
    }

    /////////////////MODEL ATTRIBUTES/////////////////

    @ModelAttribute("building")
    public List<Building> populateList(Model model) {
        List <Building> buildingList = bRepository.findAll();
        model.addAttribute("building", buildingList);
        return  buildingList;
    }
}

