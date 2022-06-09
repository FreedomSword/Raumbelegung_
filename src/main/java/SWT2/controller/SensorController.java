package SWT2.controller;


import SWT2.model.Raum;
import SWT2.model.Sensor;
import SWT2.model.Sensortyp;
import SWT2.repository.RaumRepository;
import SWT2.repository.SensorRepository;
import SWT2.repository.SensortypRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @Autowired
    private SensortypRepository sensortypRepository;



    @GetMapping("/showSensor")
    public ModelAndView showSensor() {
        ModelAndView mav = new ModelAndView("sensor");
        List<Sensor> list = sensorRepository.findAll();
        mav.addObject("sensor", list);
        return  mav;
    }
    @GetMapping("/sensorenFiltered")
    public ModelAndView sensorenFiltered(@RequestParam int raumid) {
        ModelAndView mav = new ModelAndView("sensorenFiltered");
        Raum r = raumRepository.getById(raumid);
        List<Sensor> list = sensorRepository.findAllSensors(raumid);
        mav.addObject("raum", r );
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
        Optional<Raum> optionalRaum = raumRepository.findById(sensor.getRaum().getId());
        Raum raum = optionalRaum.get();
        sensor.setRaum(raum);
        sensorRepository.save(sensor);
        return new RedirectView("/showSensor");
    }

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
    @ModelAttribute("raueme")
    public List<Raum> populateList(@NotNull Model model) {
        List<Raum> rauemeList = raumRepository.findAll();
        model.addAttribute("raueme", rauemeList);
        return  rauemeList;
    }

    @ModelAttribute("sensortyp")
    public List<Sensortyp> populateListSensortyp(Model model) {
        List <Sensortyp> sensortypList = sensortypRepository.findAll();
        model.addAttribute("sensortypen", sensortypList);
        return  sensortypList;
    }
}

