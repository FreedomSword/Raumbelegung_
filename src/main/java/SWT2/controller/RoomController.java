package SWT2.controller;

import SWT2.model.*;
import SWT2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository rRepository;

    @Autowired
    private BuildingRepository bRepository;

    @Autowired
    private SensorRepository sRepository;

    @Autowired
    private ReservationRepository resRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private ActorRepository aRepository;

    @Autowired
    private ActortypeRepository atRepository;
    /////////////////TABLE VIEWS/////////////////



    /////////////////ADDING/////////////////

    //Save the Room to the Database

    @PostMapping("/saveRoomInBuilding")
    public RedirectView saveRoom(@ModelAttribute Room room, @RequestParam int buildingId, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        room.setPhoto(fileName);

        String uploadDir = "room-photos/" + room.getName();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Building building = (bRepository.findById(buildingId)).get();
        room.setBuilding(building);
        room.setCurrentLightLevel(10);
        room.setCurrentTemperature(20);

        rRepository.save(room);


        Actor lightActor = new Actor();
        lightActor.setRoom(room);
        lightActor.setActortype(atRepository.findByName("Lichtsteuerung"));
        aRepository.save(lightActor);
        Actor tempActor = new Actor();
        tempActor.setRoom(room);
        tempActor.setActortype(atRepository.findByName("Temperatursteuerung"));
        aRepository.save(tempActor);


        rRepository.save(room);
        return new RedirectView("/buildingDetails?buildingId=" + buildingId );
    }
    @GetMapping("/roomDetails")
    public ModelAndView showSensorListOfRoom(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomDetails");
        List <Building> listb = bRepository.findAll();
        mav.addObject("buildings", listb);
        Room room = rRepository.findById(roomId).get();


        User user = uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);


        List<Reservation> reservations = resRepository.findAllByDate(LocalDate.now().toString(), roomId);
        mav.addObject("reservations", reservations);
        List<Sensor> list = sRepository.findAllSensors(roomId);
        mav.addObject("room", room );
        mav.addObject("sensor", list);
        return  mav;
    }



    //Show room add form by klicking on "Add Room" button in buildings view without needed to insert building
    @GetMapping("/addRoomInBuildingForm")
    public ModelAndView showRoomAddForm(@RequestParam int buildingId) {
        ModelAndView mav = new ModelAndView("addRoomInBuildingForm");
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
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
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
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

