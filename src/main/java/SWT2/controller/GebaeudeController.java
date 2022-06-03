package SWT2.controller;

import SWT2.dao.GebaeudeDAO;
import SWT2.model.Gebaeude;
import SWT2.repository.GebaeudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class GebaeudeController {


    //JDBC
    /*
    @Autowired
    private GebaeudeDAO gDAO;

    @GetMapping("/gebaeude")
    public List<Gebaeude> getGebaeude() {
        return gDAO.getAll();
    }
    @GetMapping("/gebaeude/{id}")
    public Gebaeude getGebaeudeById(@PathVariable int id) {
        return gDAO.getById(id);
    }

    @PostMapping("/gebaeude")
    public String saveGebaeude(@RequestBody Gebaeude gebaeude) {
        return gDAO.save(gebaeude)+ " Gebäude gespeichert";
    }

    @PutMapping("/gebaeude/{id}")
    public String updateGebaeude(@RequestBody Gebaeude gebaeude, @PathVariable int id) {
        return gDAO.update(gebaeude, id) + " Gebäude aktualisiert";
    }

    @DeleteMapping("/gebaeude/{id}")
    public String deleteGebaeudeById(@PathVariable int id) {
        return gDAO.delete(id) + " Gebäude wurden gelöscht";
    }
    */

    @Autowired
    private GebaeudeRepository gRepo;

    @GetMapping("/showGebaeude")
    public ModelAndView showGebaeude() {
        ModelAndView mav = new ModelAndView("gebaeude");
        List <Gebaeude> list = gRepo.findAll();
        mav.addObject("gebaeude", list);
        return  mav;
    }

    @GetMapping("/addGebaeudeForm")
    public ModelAndView addGebauedeForm() {
        ModelAndView mav = new ModelAndView("addGebaeudeForm");
        Gebaeude newGebaeude = new Gebaeude();
        mav.addObject("gebaeude", newGebaeude);
        return mav;
    }

    @PostMapping("/saveGebaeude")
    public RedirectView saveGebaeude(@ModelAttribute Gebaeude gebaeude) {
        gRepo.save(gebaeude);
        return new RedirectView("/showGebaeude");
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long gebaeudeId) {
        ModelAndView mav = new ModelAndView("addGebaeudeForm");
        Gebaeude gebaeude = gRepo.findById(gebaeudeId).get();
        mav.addObject("gebaeude", gebaeude);
        return mav;
    }

    @GetMapping("/deleteGebaeude")
        public RedirectView deleteGebaeude(@RequestParam Long gebaeudeId) {
        gRepo.deleteById(gebaeudeId);
        return new RedirectView("/showGebaeude");
        }

}
