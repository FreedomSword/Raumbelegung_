package SWT2.controller;

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
import java.util.List;

@RestController
public class RoomController {


    @Autowired
    Repo repo;

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
//        repo.saveRoom(room);
        repo.save(room);
        return new RedirectView("/buildingDetails?buildingId=" + buildingId );
    }
    @GetMapping("/roomDetails")
    public ModelAndView showSensorListOfRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomDetails");
        Room room = repo.findRoomById(roomId);


        User user = repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);


        List<Reservation> reservations = repo.findReservations(roomId);
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
    public ModelAndView showRoomUpdateForm(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("addRoomInBuildingForm");
        Room room = repo.findRoomById(roomId);
        Building building = repo.findBuildingById(room.getBuilding().getBid());
        mav.addObject("building", building);
        mav.addObject("room", room);
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

