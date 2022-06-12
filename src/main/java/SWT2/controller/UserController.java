package SWT2.controller;

import SWT2.model.Role;
import SWT2.model.User;
import SWT2.repository.RoleRepository;
import SWT2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserRepository uRepository;

    @Autowired
    RoleRepository roleRepository;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role roleUser = roleRepository.findByName("User");
        user.addRole(roleUser);

        uRepository.save(user);

        return "register_success";
    }
}
