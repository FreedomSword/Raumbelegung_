package SWT2.controller;

import SWT2.model.*;
import SWT2.repository.BuildingRepository;
import SWT2.repository.ReservationRepository;
import SWT2.repository.RoleRepository;
import SWT2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReservationRepository resRepository;

    @Autowired
    private BuildingRepository bRepository;
    @GetMapping("")

    public String viewHomePage() {


        return "index";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        user.setUid(uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUid());
        user.setEmail(uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role roleUser = roleRepository.findByName("User");
        user.addRole(roleUser);

        uRepository.save(user);

        return "login";
    }

    @PostMapping("/account_save")
    public RedirectView saveUpdate(@ModelAttribute User user) {
        User myUser = uRepository.getById(user.getUid());
        myUser.setEmail(user.getEmail());
        myUser.setFull_name(user.getFull_name());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        myUser.setPassword(encodedPassword);

        uRepository.save(myUser);

        return new RedirectView("/login");

    }

    @GetMapping("/updateUser")
    public ModelAndView updateUser() {
        ModelAndView mav = new ModelAndView("myAccountUpdate");
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
        User user = uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);
        return mav;
    }


    @GetMapping("/myAccount")
        public ModelAndView showMyAccount() {
        ModelAndView mav = new ModelAndView("myAccount");
        List <Building> list = bRepository.findAll();
        mav.addObject("buildings", list);
            User user = uRepository.getUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            mav.addObject("user", user);

            List<Reservation> reservations = resRepository.findAllByUser(user.getUid());
            mav.addObject("reservation", reservations);
            return mav;
        }
}
