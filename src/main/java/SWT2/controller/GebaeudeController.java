package SWT2.controller;

import SWT2.model.Gebaeude;
import SWT2.repository.GebaeudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class GebaeudeController {
    @Autowired
    private GebaeudeRepository gebaeudeRepository;

    @GetMapping("/showGebaeude")
    public ModelAndView showGebaeude() {
        ModelAndView mav = new ModelAndView("gebaeude");
        List <Gebaeude> list = gebaeudeRepository.findAll();
        mav.addObject("gebaeude", list);
        return  mav;
    }

    @GetMapping("/addGebaeudeForm")
    public ModelAndView addGebaeudeForm() {
        ModelAndView mav = new ModelAndView("addGebaeudeForm");
        Gebaeude newGebaeude = new Gebaeude();
        mav.addObject("gebaeude", newGebaeude);
        return mav;
    }

    @PostMapping("/saveGebaeude")
    public RedirectView saveGebaeude(@ModelAttribute Gebaeude gebaeude) {
        gebaeudeRepository.save(gebaeude);
        return new RedirectView("/showGebaeude");
    }

    @GetMapping("/showGebaeudeUpdateForm")
    public ModelAndView showGebaeudeUpdateForm(@RequestParam int gebaeudeId) {
        ModelAndView mav = new ModelAndView("addGebaeudeForm");
        Gebaeude gebaeude = gebaeudeRepository.findById(gebaeudeId).get();
        mav.addObject("gebaeude", gebaeude);
        return mav;
    }

    @GetMapping("/deleteGebaeude")
        public RedirectView deleteGebaeude(@RequestParam int gebaeudeId) {
        gebaeudeRepository.deleteById(gebaeudeId);
        return new RedirectView("/showGebaeude");
        }

}
