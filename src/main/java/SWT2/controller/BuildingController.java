package SWT2.controller;


import SWT2.model.Building;
import SWT2.model.Room;
import SWT2.model.User;
import SWT2.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BuildingController {

    @Autowired
    Repo repo;



    /////////////////TABLE VIEWS/////////////////

    //Show Buildings

    @GetMapping("/index")
    public ModelAndView index() {
            ModelAndView mav = new ModelAndView("index");

        List<Building>list = repo.findAllBuildings();
            mav.addObject("buildings", list);

            List<Integer> placeAmount = new ArrayList<Integer>();
            List<Integer> roomAmount = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++)
            {
                int amountRooms = 0;
                int amountPlaces = 0;

                List<Room>  rooms = repo.findRooms(list.get(i).getBid());

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
        List <Building> list = repo.findAllBuildings();
        mav.addObject("building", list);
        return  mav;
    }

    /////////////////ADDING/////////////////

    //Save building to the database
    @PostMapping("/saveBuilding")
    public RedirectView saveBuilding(@ModelAttribute Building building, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        building.setPhoto(fileName);

       repo.saveBuilding(building);

        String uploadDir = "building-photos/" + building.getName();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/buildingDetails?buildingId=" +building.getBid());
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
        Building building = repo.findBuildingById(buildingId);
        mav.addObject("building", building);
        return mav;
    }

    //Delete the building from database
    @GetMapping("/deleteBuilding")
        public RedirectView deleteBuilding(@RequestParam int buildingId) {
        repo.deleteBuildingById(buildingId);
        return new RedirectView("/showBuilding");
    }

    //Show Rooms in explicit Building
    @GetMapping("/buildingDetails")
    public ModelAndView showRoomListOfBuilding(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("buildingDetails");
        User user = repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);

        Building building = repo.getBuildingById(buildingId);
        mav.addObject("building", building );

        List<Room> list = repo.findRooms(buildingId);
        mav.addObject("room", list);

        List<Building> buildings = repo.findAllBuildings();
        mav.addObject("buildings", buildings);



        return  mav;
    }
}
