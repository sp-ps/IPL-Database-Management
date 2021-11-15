package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "/season")
@Controller
public class SeasonsController {

    @Autowired
    private SeasonsRepository seasonsRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path="/")
    public String index(){
        return "indexSeason";
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addSeason(@RequestParam Team team,@RequestParam Integer season){
        Seasons s = new Seasons(season,team);
        seasonsRepository.save(s);
        return "Saved!";
    }

    @GetMapping(path="/see")
    public @ResponseBody Seasons seeSeason(@RequestParam Integer team, @RequestParam Integer seasonId){
        Seasons season = seasonsRepository.findByTeamIdAndSeason(team, seasonId);
        return season;
    }

    @PostMapping(path="/register")
    public @ResponseBody String saveStudent(@ModelAttribute Seasons seasons, Model model) {
        // logic to process input data
        seasonsRepository.save(seasons);
        return "Saved a Season.";
    }

    @PostMapping(path="/newRegister")
    public String saveTeam(@RequestParam Integer season, @RequestParam List<Team> teams, Model model){
        for (Team team:teams
             ) {
            Seasons s = new Seasons();
            s.setTeam(team);
            s.setSeason(season);
            seasonsRepository.save(s);
        }
//        Iterable<Seasons> seasons = seasonsRepository.findAllBySeasonId(season);
//        model.addAttribute("seasons",seasons);
//        model.addAttribute("seasonId", season);
        //System.out.println(seasons);
        return addDetails(season,model);
    }

    @GetMapping(path="/addDetails")
    public String addDetails(@RequestParam Integer season, Model model){
        Iterable<Seasons> seasons = seasonsRepository.findAllBySeasonId(season);
        model.addAttribute("seasons",seasons);
        model.addAttribute("seasonId", season);
        //System.out.println(seasons);
        return "addDetails";
    }

    @GetMapping(path="/continue")
    public String continueSeason( Model model){
        Iterable<Integer> seasons = seasonsRepository.findUniqueSeason();
        model.addAttribute("seasons",seasons);
        return "continueSeason";
    }

    @GetMapping(path="/continue/{seasonId}")
    public String continueSeason1(@PathVariable Integer seasonId, Model model){
        model.addAttribute("seasonId", seasonId);
        return "continueSeason1";
    }

    @GetMapping(path="/addDetails1/{seasonId}")
    public String addDetails1(@PathVariable Integer seasonId, Model model){
        Iterable<Seasons> seasons = seasonsRepository.findAllBySeasonId(seasonId);
        model.addAttribute("seasons",seasons);
        model.addAttribute("seasonId", seasonId);
        //System.out.println(seasons);
        return "addDetails";
    }

    @GetMapping(path="/addDetails1")
    public String addDetails1(@RequestParam Integer team, @RequestParam Integer seasonId,Model model){
        Seasons season = seasonsRepository.findByTeamIdAndSeason(team, seasonId);
        Iterable<Player> players = playerRepository.findAll();
        model.addAttribute("seasonInfo", season);
        model.addAttribute("players", players);
        return "addDetails1";
    }

    @GetMapping(path="/addDetails1/{seasonId}/{team}")
    public String addDetails2(@PathVariable Integer team, @PathVariable Integer seasonId,Model model){
        Seasons season = seasonsRepository.findByTeamIdAndSeason(team, seasonId);
        Iterable<Player> players = playerRepository.findAll();
        model.addAttribute("seasonInfo", season);
        model.addAttribute("players", players);
        return "addDetails1";
    }



//    @RequestMapping(path="/addDetails1", params = {"addPlayerRow"})
//    public String addPlayerRow(Seasons season, Model model){
//        if( season.getCoaches().size() == 0)
//        {
//            List<String> c = null;
//            c.add(new String());
//            season.setCoaches(c);
//        }
//        else
//            season.getCoaches().add(new String());
//
//        model.addAttribute("seasonInfo", season);
//        return "addDetails1";
//    }



    @GetMapping(path="/add1")
    public String registerSeason(Model model){
        //Seasons seasons = new Seasons();
        Iterable<Team> teams = teamRepository.findAll();
       // model.addAttribute("seasons",seasons);
        model.addAttribute("teams", teams);
        return "registerSeason";
    }


//    @PostMapping(path="/addCoach")
//    public String addCoach( @RequestParam Integer team, @RequestParam Integer season, @RequestParam String coach, Model model){
//        Seasons s = seasonsRepository.findByTeamIdAndSeason(team, season);
//        s.setCoaches(coach);
//        seasonsRepository.save(s);
//        model.addAttribute("seasonInfo", s);
//        String path = "redirect:/season/addDetails1/" + season.toString() + "/" + team.toString();
//        return path;
//    }

    @PostMapping(path="/addCoach")
    public RedirectView addCoach(@RequestParam Integer team, @RequestParam Integer season, @RequestParam String coach, RedirectAttributes redirectAttributes, Model model){
        Seasons s = seasonsRepository.findByTeamIdAndSeason(team, season);
        s.setCoaches(coach);
        seasonsRepository.save(s);
        String userMessage = coach + " added successfully as a coach";
        String path = "/season/addDetails1/" + season.toString() + "/" + team.toString();
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }

//    @PostMapping(path = "/addOwner")
//    public String addOwners(@RequestParam Team team, @RequestParam Integer season,@RequestParam String owner, Model model){
//        Seasons s = seasonsRepository.findByTeamAndSeasonId(team, season);
//        s.setOwners(owner);
//        seasonsRepository.save(s);
//        String path = "redirect:/season/addDetails1/" + season.toString() + "/" + team.getTeamId().toString();
//        return path;
//    }

    @PostMapping(path="/addOwner")
    public RedirectView addOwners(@RequestParam Team team, @RequestParam Integer season,@RequestParam String owner, RedirectAttributes redirectAttributes, Model model){
        Seasons s = seasonsRepository.findByTeamAndSeasonId(team, season);
        s.setOwners(owner);
        seasonsRepository.save(s);
        String userMessage = owner + " added successfully as an owner";
        String path = "/season/addDetails1/" + season.toString() + "/" + team.getTeamId().toString();
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }

//    @PostMapping(path="/addPlayer")
//    public String addPlayers( @RequestParam Team team, @RequestParam Integer season,
//                                            @RequestParam Integer playerId){
//        Player p = playerRepository.findByPlayerId(playerId);
//        Seasons s = seasonsRepository.findByTeamAndSeasonId(team, season);
//        s.setPlayers(p);
//        seasonsRepository.save(s);
//        String path = "redirect:/season/addDetails1/" + season.toString() + "/" + team.getTeamId().toString();
//        return path;
//    }

    @PostMapping(path="/addPlayer")
    public RedirectView addPlayers( @RequestParam Team team, @RequestParam Integer season,@RequestParam Integer playerId, RedirectAttributes redirectAttributes, Model model){
        Player p = playerRepository.findByPlayerId(playerId);
        Seasons s = seasonsRepository.findByTeamAndSeasonId(team, season);
        Iterable<Seasons> seasons = seasonsRepository.findAllBySeasonId(season);
        boolean playerExists = false;
        String playerTeam = "";
        for(Seasons season1 : seasons){
            if(season1.getPlayers().contains(p)){
                playerExists = true;
                playerTeam = season1.getTeam().getTeamName();
            }
        }
        if(playerExists){
            String dangerMessage = playerRepository.findByPlayerId(playerId).getPlayerName() +" already exists in this season with "+ playerTeam;
            redirectAttributes.addFlashAttribute("dangerMessage", dangerMessage);
        }
        else{
            s.setPlayers(p);
            seasonsRepository.save(s);
            String userMessage = p.getPlayerName() + " added successfully as a player";
            redirectAttributes.addFlashAttribute("userMessage", userMessage);
        }
        String path = "/season/addDetails1/" + season.toString() + "/" + team.getTeamId().toString();
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }

//    @PostMapping(path="/removePlayer")
//    public String removePlayer(@RequestParam Integer playerId, @RequestParam Integer seasonId, @RequestParam Integer teamId){
//        Player p = playerRepository.findByPlayerId(playerId);
//        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
//        s.getPlayers().remove(p);
//        seasonsRepository.save(s);
//        String path = "redirect:/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
//        return path;
//    }

    @PostMapping(path="/removePlayer")
    public RedirectView removePlayer(@RequestParam Integer playerId, @RequestParam Integer seasonId, @RequestParam Integer teamId, RedirectAttributes redirectAttributes, Model model){
        Player p = playerRepository.findByPlayerId(playerId);
        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
        s.getPlayers().remove(p);
        seasonsRepository.save(s);
        String userMessage = p.getPlayerName() + " removed successfully as a player";
        String path = "/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }


//    @PostMapping(path = "/removeCoach")
//    public String removeCoach(@RequestParam String coach, @RequestParam Integer seasonId, @RequestParam Integer teamId){
//        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
//        s.getCoaches().remove(coach);
//        seasonsRepository.save(s);
//        String path = "redirect:/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
//        return path;
//    }

    @PostMapping(path="/removeCoach")
    public RedirectView removeCoach(@RequestParam String coach, @RequestParam Integer seasonId, @RequestParam Integer teamId, RedirectAttributes redirectAttributes, Model model){
        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
        s.getCoaches().remove(coach);
        seasonsRepository.save(s);
        String userMessage = coach + " removed successfully as a coach";
        String path = "/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }

//    @PostMapping(path = "/removeOwner")
//    public String removeOwner(@RequestParam String owner, @RequestParam Integer seasonId, @RequestParam Integer teamId){
//        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
//        s.getOwners().remove(owner);
//        seasonsRepository.save(s);
//        String path = "redirect:/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
//        return path;
//    }

    @PostMapping(path="/removeOwner")
    public RedirectView removeOwner(@RequestParam String owner, @RequestParam Integer seasonId, @RequestParam Integer teamId, RedirectAttributes redirectAttributes, Model model){
        Seasons s = seasonsRepository.findByTeamIdAndSeason(teamId, seasonId);
        s.getOwners().remove(owner);
        seasonsRepository.save(s);
        String userMessage = owner + " removed successfully as an owner";
        String path = "/season/addDetails1/" + seasonId.toString() + "/" + teamId.toString();
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        RedirectView redirectView = new RedirectView(path, true);
        return redirectView;
    }


}
