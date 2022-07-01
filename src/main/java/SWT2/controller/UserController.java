package SWT2.controller;

import SWT2.model.*;
import SWT2.repository.FactoryService;
import SWT2.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    Repo repo;

    @Autowired
    FactoryService fs;

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
        user.setUid(repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUid());
        user.setEmail(repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role roleUser = repo.findRoleByName("User");
        user.addRole(roleUser);

        fs.save(user);

        return "login";
    }

    @PostMapping("/account_save")
    public RedirectView saveUpdate(@ModelAttribute User user) {
        User myUser = repo.findUserById(user.getUid());
        myUser.setEmail(user.getEmail());
        myUser.setFull_name(user.getFull_name());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        myUser.setPassword(encodedPassword);

        fs.save(myUser);

        return new RedirectView("/login");

    }

    @GetMapping("/updateUser")
    public ModelAndView updateUser() {
        ModelAndView mav = new ModelAndView("myAccountUpdate");
        User user = repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("user", user);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);

        return mav;
    }


    @GetMapping("/myAccount")
        public ModelAndView showMyAccount() {
        ModelAndView mav = new ModelAndView("myAccount");
            User user = repo.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            mav.addObject("user", user);

            List<Reservation> reservations = repo.findReservationsByUserId(user.getUid());
            mav.addObject("reservation", reservations);

        List<Building>buildingList = repo.findAllBuildings();
        mav.addObject("buildings", buildingList);
            return mav;
        }
}
