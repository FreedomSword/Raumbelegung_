package SWT2.controller;

import SWT2.model.Gebaeude;
import SWT2.model.Raum;
import SWT2.repository.GebaeudeRepository;
import SWT2.repository.RaumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
public class RaumController {

    @Autowired
    private RaumRepository raumRepository;

    @Autowired
    private GebaeudeRepository gebaeudeRepository;



    @GetMapping("/showRaum")
    public ModelAndView showRaum() {
        ModelAndView mav = new ModelAndView("raum");
        List<Raum> list = raumRepository.findAll();
        mav.addObject("raum", list);
        return  mav;
    }

    @GetMapping("/addRaumForm")
    public ModelAndView addRaumForm() {
        ModelAndView mav = new ModelAndView("addRaumForm");
        Raum newRaum = new Raum();
        mav.addObject("raum", newRaum);
        return mav;
    }

    @PostMapping("/saveRaum")
    public RedirectView saveRaum(@ModelAttribute Raum raum) {
        System.out.println(raum.getId());
        System.out.println(raum.getAkt_belegung());
        System.out.println(raum.getMax_belegung());
        System.out.println(raum.getTyp());
        System.out.println(gebaeudeRepository.findById(raum.getGebaeude().getId()).toString());
        Optional<Gebaeude> optionalGebaeude = gebaeudeRepository.findById(raum.getGebaeude().getId());
        Gebaeude gebaeude = optionalGebaeude.get();
        raum.setGebaeude(gebaeude);
        System.out.println(gebaeude);
        raumRepository.save(raum);

        return new RedirectView("/showRaum");
    }

    @GetMapping("/assignGebaeude/{id}")
    public ModelAndView assignGebaeude(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("assignGebaeude");
        List<Gebaeude> list = gebaeudeRepository.findAll();

        Raum raum = raumRepository.getById(id);
        mav.addObject("raum", raum);
        mav.addObject("gebaeude", list);
        return  mav;
    }

    @GetMapping("/showRaumUpdateForm")
    public ModelAndView showRaumUpdateForm(@RequestParam int raumId) {
        ModelAndView mav = new ModelAndView("addRaumForm");
        Raum raum = raumRepository.findById(raumId).get();
        mav.addObject("raum", raum);
        return mav;
    }

    @GetMapping("/deleteRaum")
    public RedirectView deleteRaum(@RequestParam int raumId) {
        raumRepository.deleteById(raumId);
        return new RedirectView("/showRaum");
    }

}

