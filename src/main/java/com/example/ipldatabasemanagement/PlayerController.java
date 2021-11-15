package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path="/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path="/")
    public String index(Model model) {
        model.addAttribute("players", playerRepository.findAll());
        return "indexPlayer";
    }

//    @PostMapping(path = "/register")
//    public  String addPlayer(@RequestParam String name, Model model){
//        String userMessage = name + " has been added";
//        Player p = new Player();
//        p.setPlayerName(name);
//        playerRepository.save(p);
//        model.addAttribute("userMessage", userMessage);
//        return "redirect:/player/add";
//    }

    @PostMapping(path = "/register")
    public RedirectView addPlayer(@RequestParam String name, RedirectAttributes redirectAttributes){

        if( playerRepository.findByPlayerName(name) != null){
            String errorMessage = name + " already exists";
            RedirectView rv = new RedirectView("/player/add", true);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return rv;
        }

        Player p = new Player();
        p.setPlayerName(name);
        playerRepository.save(p);
        String userMessage = name + " has been added";
        RedirectView rv = new RedirectView("/player/add", true);
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return rv;
    }


//    @PostMapping(path="/delete")
//    public String deletePlayer(@RequestParam Integer playerId, Model model){
//        String userMessage = playerRepository.findById(playerId).get().getPlayerName() + " has been deleted";
//        playerRepository.deleteById(playerId);
//        model.addAttribute("userMessage", userMessage);
//        return "redirect:/player/delete";
//    }

    @PostMapping(path="/delete")
    public RedirectView deletePlayer(@RequestParam Integer playerId, RedirectAttributes redirectAttributes){
        String userMessage = playerRepository.findById(playerId).get().getPlayerName() + " has been deleted";
        playerRepository.deleteById(playerId);
        RedirectView rv = new RedirectView("/player/delete", true);
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        return rv;
    }

    @GetMapping(path="/delete")
    public String deletePlayer(Model model){
        Iterable<Player> players = playerRepository.findAll();
        model.addAttribute("players", players);
        return "deletePlayer";
    }

    @GetMapping(path="/add")
    public String registerPlayer(Model model){
        return "registerPlayer";
    }

    @GetMapping(path="/get")
    public @ResponseBody Iterable<Player> getPlayers(){
        return playerRepository.findAll();
    }

}
