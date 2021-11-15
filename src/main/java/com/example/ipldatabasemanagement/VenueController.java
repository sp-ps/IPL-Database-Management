package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = "/venue")
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addVenue(@RequestParam String name){
        Venue v = new Venue();
        v.setVenueName(name);
        venueRepository.save(v);
        return "Saved!";
    }

    @GetMapping(path="/")
    public String index(){
        return "indexVenue";
    }

    @GetMapping(path="/get")
    public @ResponseBody Iterable<Venue> getVenue(){
        return venueRepository.findAll();
    }


    @PostMapping(path = "/register")
    public RedirectView addVenue(@RequestParam String name, RedirectAttributes redirectAttributes){
        if(venueRepository.findByVenueName(name) != null){
            redirectAttributes.addFlashAttribute("errorMessage", "Venue already exists");
            return new RedirectView("/venue/add");
        }
        Venue v = new Venue();
        v.setVenueName(name);
        venueRepository.save(v);
        String userMessage = "Venue " + name + " has been added";
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return new RedirectView("/venue/add");
    }


    @GetMapping(path="/add")
    public String addVenue(){
        return "addVenue";
    }

    @PostMapping(path = "/delete")
    public RedirectView deleteVenue(@RequestParam Integer venueId, RedirectAttributes redirectAttributes){
        Venue v = venueRepository.findById(venueId).get();
        String userMessage = "Venue " + v.getVenueName() + " has been deleted";
        venueRepository.delete(v);
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return new RedirectView("/venue/delete");
    }


    @GetMapping(path="/delete")
    public String deleteVenue(Model model){
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        return "deleteVenue";
    }



}
