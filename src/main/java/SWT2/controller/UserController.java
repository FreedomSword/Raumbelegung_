package SWT2.controller;


import SWT2.repository.RoleRepository;
import SWT2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class UserController {

    @Autowired
    UserRepository uRepository;

    @Autowired
    RoleRepository roleRepository;

//    @GetMapping("")
//    public String viewHomePage() {
//
//
//        return "index";
//    }


//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//
//        return "signup_form";
//    }
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("user", new User());
//
//        return "login";
//    }

//    @PostMapping("/process_register")
//    public String processRegister(User user) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        user.setEnabled(true);
//
//        Role roleUser = roleRepository.findByName("User");
//        user.addRole(roleUser);
//
//        uRepository.save(user);
//
//        return "register_success";
//    }
}
