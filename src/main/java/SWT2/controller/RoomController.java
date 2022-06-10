package SWT2.controller;

import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.repository.BuildingRepository;
import SWT2.repository.RoomRepository;
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

    /////////////////TABLE VIEWS/////////////////

    //Show Rooms
    @GetMapping("/showRoom")
    public ModelAndView showRoomList() {
        ModelAndView mav = new ModelAndView("room");
        List<Room> list = rRepository.findAll();
        mav.addObject("room", list);
        return  mav;
    }

    //Show Rooms in explicit Building
    @GetMapping("/showBuildingRooms")
    public ModelAndView showRoomListOfBuilding(@RequestParam int buildingId) {
            ModelAndView mav = new ModelAndView("roomsInBuilding");
            Building building = bRepository.getById(buildingId);
            List<Room> list = rRepository.findAllRooms(buildingId);
            mav.addObject("building", building );
            mav.addObject("room", list);
            return  mav;
    }

    /////////////////ADDING/////////////////

    //Save the Room to the Database
    @PostMapping("/saveRoom")
    public RedirectView saveRoom(@ModelAttribute Room room, HttpServletRequest request) {

        String referer = request.getHeader("Referer");
        Optional<Building> buildingOptional = bRepository.findById(room.getBuilding().getBid());
        Building building = buildingOptional.get();
        room.setBuilding(building);
        rRepository.save(room);
        return new RedirectView("/showRoom");
    }

    @PostMapping("/saveRoomInBuilding")
    public RedirectView saveRoom(@ModelAttribute Room room, @RequestParam int buildingId) {
        Building building = (bRepository.findById(buildingId)).get();
        room.setBuilding(building);
        rRepository.save(room);
        return new RedirectView("/showRoom");
    }

/*    //Show room add form by klicking on "Add Room" button in rooms view
    @GetMapping("/addRoomForm")
    public ModelAndView showAddRoomForm() {
        ModelAndView mav = new ModelAndView("addRoomForm");
        Room room = new Room();
        mav.addObject("room", room);

        return mav;
    }*/

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
        rRepository.deleteById(roomId);
        return new RedirectView("/showRoom");
    }

    //Show room update form
    @GetMapping("/UpdateRoomForm")
    public ModelAndView showRoomUpdateForm(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("addRoomForm");
        Room room = rRepository.findById(roomId).get();
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

