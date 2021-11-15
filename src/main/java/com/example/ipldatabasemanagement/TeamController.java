package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path="/team")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping(path="/")
    public String Index(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "indexTeam";
    }

    @GetMapping(path="/add")
    public String addTeam(){
        return "addTeam";
    }

//    @PostMapping(path="/add")
//    public String addTeam(@RequestParam String name, @RequestParam String petName){
//        Team t = new Team();
//        t.setTeamName(name);
//        t.setPetName(petName);
//        teamRepository.save(t);
//        return "redirect:/team/add";
//    }

    @PostMapping (path="/add")
    public RedirectView addTeam(@RequestParam String name, @RequestParam String petName, RedirectAttributes redirectAttributes){

        if(teamRepository.findByTeamName(name) != null){
            String errorMessage = "Team name already exists";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return new RedirectView("/team/add");
        }

        if(teamRepository.findByPetName(petName) != null){
            String errorMessage = "Team Pet name already exists";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return new RedirectView("/team/add");
        }


        Team t = new Team();
        t.setTeamName(name);
        t.setPetName(petName);
        teamRepository.save(t);
        String userMessage = t.getTeamName() + " has been added";
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return new RedirectView("/team/add");
    }

    @GetMapping(path="/delete")
    public String deleteTeam(Model model){
        Iterable<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);
        return "deleteTeam";
    }

//    @PostMapping(path = "/delete")
//    public String deleteTeam(@RequestParam Integer teamId){
//        teamRepository.deleteById(teamId);
//        return "redirect:/team/delete";
//    }

    @PostMapping (path = "/delete")
    public RedirectView deleteTeam(@RequestParam Integer teamId, RedirectAttributes redirectAttributes){
        String userMessage = teamRepository.findById(teamId).get().getTeamName() + " has been deleted";
        teamRepository.deleteById(teamId);
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return new RedirectView("/team/delete");
    }



    @GetMapping(path="/get")
    public @ResponseBody Iterable<Team> getTeam(){
        return  teamRepository.findAll();
    }
}
