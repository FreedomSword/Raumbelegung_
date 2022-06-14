package SWT2.controller;


import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.repository.BuildingRepository;
import SWT2.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BuildingController {
    @Autowired
    private BuildingRepository bRepository;

    @Autowired
    private RoomRepository rRepository;

    /////////////////TABLE VIEWS/////////////////

    //Show Buildings

    @GetMapping("/index")
    public ModelAndView index() {
            ModelAndView mav = new ModelAndView("index");
            List <Building> list = bRepository.findAll();

            mav.addObject("building", list);
            return  mav;
        }

    @GetMapping("/showBuilding")
    public ModelAndView showBuilding() {
        ModelAndView mav = new ModelAndView("building");
        List <Building> list = bRepository.findAll();
        mav.addObject("building", list);
        return  mav;
    }

    /////////////////ADDING/////////////////

    //Save building to the database
    @PostMapping("/saveBuilding")
    public RedirectView saveBuilding(@ModelAttribute Building building) {
        bRepository.save(building);
        return new RedirectView("/showBuilding");
    }

    //Show building add form by klicking on "Add Building" button at buildings view
    @GetMapping("/addBuildingForm")
    public ModelAndView addBuildingForm() {
        ModelAndView mav = new ModelAndView("addBuildingForm");
        Building building = new Building();
        mav.addObject("building", building);
        return mav;
    }

    /////////////////DELETE AND UPDATE/////////////////

    //Show building update form
    @GetMapping("/BuildingUpdateForm")
    public ModelAndView showBuildingUpdateForm(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("addBuildingForm");
        Building building = bRepository.findById(buildingId).get();
        mav.addObject("building", building);
        return mav;
    }

    //Delete the building from database
    @GetMapping("/deleteBuilding")
        public RedirectView deleteBuilding(@RequestParam int buildingId) {
        bRepository.deleteById(buildingId);
        return new RedirectView("/showBuilding");
    }

    //Show Rooms in explicit Building
    @GetMapping("/buildingDetails")
    public ModelAndView showRoomListOfBuilding(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("buildingDetails");
        Building building = bRepository.getById(buildingId);
        List<Room> list = rRepository.findAllRooms(buildingId);
        mav.addObject("building", building );
        mav.addObject("room", list);
        return  mav;
    }
}
