package SWT2.controller;

import SWT2.model.Raum;
import SWT2.model.Sensor;
import SWT2.repository.RaumRepository;
import SWT2.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private RaumRepository raumRepository;



    @GetMapping("/showSensor")
    public ModelAndView showSensor() {
        ModelAndView mav = new ModelAndView("sensor");
        List<Sensor> sList = sensorRepository.findAll();
        List<Sensor> list = sensorRepository.findAll();
        mav.addObject("sensor", list);
        return  mav;
    }

    @GetMapping("/addSensorForm")
    public ModelAndView addSensorForm() {
        ModelAndView mav = new ModelAndView("addSensorForm");
        Sensor newSensor = new Sensor();
        mav.addObject("sensor", newSensor);
        return mav;
    }

    @PostMapping("/saveSensor")
    public RedirectView saveSensor(@ModelAttribute Sensor sensor) {
        System.out.println(sensor.getId());
        System.out.println(sensor.getRaum());
        System.out.println(sensor.getTyp());
        System.out.println(raumRepository.findById(sensor.getRaum().getId()));
        Optional<Raum> optionalRaum = raumRepository.findById(sensor.getRaum().getId());
        Raum raum = optionalRaum.get();
        sensor.setRaum(raum);
        System.out.println(raum);
        sensorRepository.save(sensor);

        return new RedirectView("/showSensor");
    }
/*
    @PostMapping("/saveRaum/{gebaeudeId}")
    public RedirectView saveRaum(@ModelAttribute Sensor raum, @PathVariable int gebaeudeId) {

        Gebaeude gebaeude = gebaeudeRepository.findById(gebaeudeId).get();
        raum.setGebaeude(gebaeude);
        senorRepository.save(raum);
        return new RedirectView("/showRaum");
    }
*/
    @GetMapping("/assignSensor/{id}")
    public ModelAndView assignSensor(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("assignGebaeude");
        List<Sensor> list = sensorRepository.findAll();
        Sensor sensor = sensorRepository.getById(id);
        mav.addObject("sensor", sensor);
        mav.addObject("sensor", list);
        return  mav;
    }

    @GetMapping("/showSensorUpdateForm")
    public ModelAndView showSensorUpdateForm(@RequestParam int sensorId) {
        ModelAndView mav = new ModelAndView("addSensorForm");
        Sensor sensor = sensorRepository.findById(sensorId).get();
        mav.addObject("sensor", sensor);
        return mav;
    }

    @GetMapping("/deleteSensor")
    public RedirectView deleteSensor(@RequestParam int sensorId) {
        sensorRepository.deleteById(sensorId);
        return new RedirectView("/showSensor");
    }

}

