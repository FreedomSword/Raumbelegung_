package SWT2.controller;


import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.model.User;
import SWT2.repository.BuildingRepository;
import SWT2.repository.RoomRepository;
import SWT2.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BuildingController {
    @Autowired
    private BuildingRepository bRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private RoomRepository rRepository;


    /////////////////TABLE VIEWS/////////////////

    //Show Buildings

    @GetMapping("/index")
    public ModelAndView index() {
            ModelAndView mav = new ModelAndView("index");
            List <Building> list = bRepository.findAll();
            mav.addObject("buildings", list);

            List<Integer> placeAmount = new ArrayList<Integer>();
            List<Integer> roomAmount = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++)
            {
                int amountRooms = 0;
                int amountPlaces = 0;
               // String roomListName = "roomsBuilding" + list.get(i).getBid();
                List<Room>  rooms = rRepository.findAllRooms(list.get(i).getBid());

                for (int y = 0; y < rooms.size(); y++) {
                    amountRooms++;
                    amountPlaces+=rooms.get(y).getMax_occupancy();
                }
                placeAmount.add(amountPlaces);
                roomAmount.add(amountRooms);



            }

            mav.addObject("rooms", roomAmount);
            mav.addObject("places", placeAmount);
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
    public RedirectView saveBuilding(@ModelAttribute Building building, @RequestParam("image") MultipartFile multipartFile) throws IOException {



        if(multipartFile.isEmpty()) {

        }

        else {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            building.setPhoto(fileName);
            String uploadDir = "building-photos/" + building.getName();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

       bRepository.save(building);



        return new RedirectView("/buildingDetails?buildingId=" +building.getBid());
    }

    //Show building add form by klicking on "Add Building" button at buildings view
    @GetMapping("/addBuildingForm")
    public ModelAndView addBuildingForm() {
        ModelAndView mav = new ModelAndView("addBuildingForm");
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
        Building building = new Building();
        mav.addObject("building", building);
        return mav;
    }

    /////////////////DELETE AND UPDATE/////////////////

    //Show building update form
    @GetMapping("/BuildingUpdateForm")
    public ModelAndView showBuildingUpdateForm(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("addBuildingForm");
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
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
        User user = uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);

        Building building = bRepository.getById(buildingId);
        mav.addObject("building", building );

        List<Room> list = rRepository.findAllRooms(buildingId);
        mav.addObject("room", list);

        List<Building> buildings = bRepository.findAll();
        mav.addObject("buildings", buildings);



        return  mav;
    }
}
