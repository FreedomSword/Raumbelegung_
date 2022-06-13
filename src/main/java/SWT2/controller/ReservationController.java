package SWT2.controller;

import SWT2.model.Reservation;
import SWT2.model.Room;
import SWT2.model.User;
import SWT2.repository.ReservationRepository;
import SWT2.repository.RoomRepository;
import SWT2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository resRepository;

    @Autowired
    UserRepository uRepository;

    @Autowired
    RoomRepository rRepository;

    @GetMapping("/ShowReservations")
    public ModelAndView showReservations(){
        ModelAndView mav = new ModelAndView("reservation");
        List<Reservation> list = resRepository.findAll();
        mav.addObject("reservation", list);
        return mav;
    }

    // save room to database
    @PostMapping("/saveReservation")
    public RedirectView saveReservation(@ModelAttribute Reservation reservation) {
       Room room = rRepository.findById(reservation.getRoom().getRid()).get();
       User user  = uRepository.findById(reservation.getUser().getUid()).get();

        reservation.setRoom(room);
        reservation.setUser(user);


        resRepository.save(reservation);
        return new RedirectView("/ShowReservations");
    }


    @GetMapping("/addReservationForm")
    public RedirectView addReservationForm(@RequestParam int roomId, @RequestParam int uId) {
        ModelAndView mav = new ModelAndView("addReservationForm");
        Reservation reservation = new Reservation();
        Room room = (rRepository.findById(roomId)).get();
        User user = (uRepository.findById(uId)).get();
        mav.addObject(room);
        mav.addObject(user);
        mav.addObject(reservation);
        return  new RedirectView();
    }

    @GetMapping("/UpdateReservationForm")
    public ModelAndView showRoomUpdateForm(@RequestParam int ReservationId) {
        ModelAndView mav = new ModelAndView("addReservationForm");
        Reservation reservation = resRepository.findById(ReservationId).get();
        mav.addObject("reservation", reservation);
        return mav;
    }

    @GetMapping("/deleteReservation")
    public RedirectView deleteReservation(@RequestParam int ReservationId) {
        resRepository.deleteById(ReservationId);
        return new RedirectView("/showReservation");
    }
}
