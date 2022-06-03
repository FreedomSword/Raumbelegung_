package SWT2.controller;

import SWT2.dao.RaumDAO;
import SWT2.model.Raum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RaumController {

    @Autowired
    private RaumDAO rDAO;

    @GetMapping("/raum")
    public List<Raum> getRaeume() {
        return rDAO.getAll();
    }

    @GetMapping("/raum/{id}")
    public Raum getRaumById(@PathVariable int id) {
        return rDAO.getById(id);
    }

    @PostMapping("/raum")
    public String saveRaum(@RequestBody Raum raum) {
        return rDAO.save(raum) + " Räume gespeichert";
    }

    @PutMapping("/raum/{id}")
    public String updateRaum(@RequestBody Raum raum, @PathVariable int id) {
        return rDAO.update(raum, id) + " Raum aktualisiert";
    }

    @DeleteMapping("raum/{id}")
    public String deleteRaumById(@PathVariable int id) {
        return rDAO.delete(id) + " Raum wurde gelöscht";

    }
}

