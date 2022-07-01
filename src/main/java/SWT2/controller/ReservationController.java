package SWT2.controller;

import SWT2.model.Reservation;
import SWT2.model.Room;
import SWT2.model.User;
import SWT2.repository.Repo;
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
    Repo repo;



    //Show all the Reservations of one Room
    @GetMapping("/roomReservations")
    public ModelAndView roomReservations(@RequestParam int roomId) {
        ModelAndView mav = new ModelAndView("roomReservations");
        Room room = repo.getRoomById(roomId);
        List<Reservation> list = repo.findReservations(roomId);
        mav.addObject("room", room );
        mav.addObject("reservation", list);
        return  mav;
    }

    //saving a reservation

    @GetMapping("/addReservationFormDate")
    public ModelAndView showReservationAddFormDate(@RequestParam int roomId,int userId) {
        ModelAndView mav = new ModelAndView("addReservationFormDate");
        Reservation reservation = new Reservation();
        Room room = repo.findRoomById(roomId);
        User user = repo.findUserById(userId);
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
        Room room = repo.findRoomById(roomId);
        User user = repo.findUserById(userId);
        
        List<Reservation> bookedSlots = repo.findReservationsByDate(date, roomId);

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
        Room room = (repo.findRoomById(roomId));
        reservation.setRoom(room);
        reservation.setUser((repo.findUserById(userId)));
        repo.saveReservations(reservation);
        return new RedirectView("/roomDetails?roomId="+roomId);
    }

    @GetMapping("/UpdateReservationForm")
    public ModelAndView showRoomUpdateForm(@RequestParam int ReservationId) {
        ModelAndView mav = new ModelAndView("addReservationForm");
        Reservation reservation = repo.findReservationById(ReservationId);
        mav.addObject("reservation", reservation);
        return mav;
    }

    @GetMapping("/deleteReservation")
    public RedirectView deleteReservation(@RequestParam int reservationId) {
        repo.deleteReservationById(reservationId);
        return new RedirectView("/myAccount");
    }

}