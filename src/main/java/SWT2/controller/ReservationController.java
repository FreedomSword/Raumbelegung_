package SWT2.controller;

import SWT2.model.Reservation;
import SWT2.model.Room;
import SWT2.model.User;
import SWT2.repository.ReservationRepository;
import SWT2.repository.RoomRepository;
import SWT2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ReservationController {

    @Autowired
    ReservationRepository resRepository;

    @Autowired
    UserRepository uRepository;

    @Autowired
    RoomRepository rRepository;

    //Show all the Reservations of one Room
    @GetMapping("/roomReservations")
    public ModelAndView roomReservations(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomReservations");
        Room room = rRepository.getById(roomId);
        List<Reservation> list = resRepository.findAllReservations(roomId);
        mav.addObject("room", room );
        mav.addObject("reservation", list);
        return  mav;
    }

    //saving a reservation

    @GetMapping("/addReservationFormDate")
    public ModelAndView showReservationAddFormDate(@RequestParam int roomId,int userId) {
        ModelAndView mav = new ModelAndView("addReservationFormDate");
        Reservation reservation = new Reservation();
        Room room = rRepository.findById(roomId).get();
        User user = uRepository.findById(userId).get();
        mav.addObject("room", room);
        mav.addObject("user", user);
        mav.addObject("reservation", reservation);
        return mav;
    }
    @GetMapping("/addReservationForm")
    public ModelAndView showReservationAddForm(@RequestParam int roomId,int userId, String date) {
        ModelAndView mav = new ModelAndView("addReservationForm");
        Reservation reservation = new Reservation();
        reservation.setDate(date);
        Room room = rRepository.findById(roomId).get();
        User user = uRepository.findById(userId).get();
        
        List<Reservation> bookedSlots = resRepository.findAllByDate(date, roomId);

        List<String> allSlots = new ArrayList<String>();
        String s = "";
        for(int i = 5; i <= 22; i++) {
            allSlots.add(i + ":00:00");
        }

        for(int i = 0; i < allSlots.size(); i++) {
            for(int y = 0; y < bookedSlots.size(); y++) {
                if(bookedSlots.get(y).getTime().equals(allSlots.get(i))) {
                    allSlots.remove(i);
                }
            }

        }
        mav.addObject("allSlots", allSlots);
        mav.addObject("room", room);
        mav.addObject("user", user);
        mav.addObject("reservation", reservation);
        return mav;
    }

    @PostMapping("/saveReservationDate")
    public RedirectView saveReservationDate(@ModelAttribute Reservation reservation, @ModelAttribute String stringDate, @RequestParam int roomId,
                                            int userId, String date) throws ParseException {
        date = date.replace(",","");
        return new RedirectView("/addReservationForm?roomId="+roomId+"&userId="+userId+"&date="+date);

    }


    @PostMapping("/saveReservation")
    public RedirectView saveReservation(@ModelAttribute Reservation reservation, @RequestParam int roomId,int userId, Errors errors) {
        Room room = (rRepository.findById(roomId)).get();
        reservation.setRoom(room);
        reservation.setUser((uRepository.findById(userId)).get());
        resRepository.save(reservation);
        return new RedirectView("/roomDetails?roomId="+roomId);
    }

    @GetMapping("/UpdateReservationForm")
    public ModelAndView showRoomUpdateForm(@RequestParam int ReservationId) {
        ModelAndView mav = new ModelAndView("addReservationForm");
        Reservation reservation = resRepository.findById(ReservationId).get();
        mav.addObject("reservation", reservation);
        return mav;
    }

    @GetMapping("/deleteReservation")
    public RedirectView deleteReservation(@RequestParam int reservationId) {
        resRepository.deleteById(reservationId);
        return new RedirectView("/myAccount");
    }

    // this method Shows All reservations of all rooms
//    @GetMapping("/ShowReservations")
//    public ModelAndView showReservations(){
//        ModelAndView mav = new ModelAndView("ShowReservations");
//        List<Reservation> list = resRepository.findAll();
//        mav.addObject("reservation", list);
//        return mav;
//    }
//
//    // save room to database
//    @PostMapping("/saveReservation")
//    public RedirectView saveReservation(@ModelAttribute Reservation reservation) {
//       Room room = rRepository.findById(reservation.getRoom().getRid()).get();
//       User user  = uRepository.findById(reservation.getUser().getUid()).get();
//
//        reservation.setRoom(room);
//        reservation.setUser(user);
//
//
//        resRepository.save(reservation);
//        return new RedirectView("/ShowReservations");
//    }


//    @GetMapping("/addReservationForm")
//    public ModelAndView addReservationForm(@RequestParam int roomId, @RequestParam int uId) {
//
//        ModelAndView mav = new ModelAndView("addReservationForm");
//        Reservation reservation = new Reservation();
//        Room room = (rRepository.findById(roomId)).get();
//        User user = (uRepository.findById(uId)).get();
//        mav.addObject(room);
//        mav.addObject(user);
//        mav.addObject(reservation);
//
//
//        return mav;
//    }

}