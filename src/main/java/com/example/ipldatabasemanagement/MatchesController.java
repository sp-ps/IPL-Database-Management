package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.Access;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/matches")
public class MatchesController {

    @Autowired
    private MatchesRepository matchesRepository;
    @Autowired
    private SeasonsRepository seasonsRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BattingPerformanceRepository battingPerformanceRepository;

    @Autowired
    private BowlingPerformanceRepository bowlingPerformanceRepository;


    @GetMapping(path="/createSchedule")
    public String createSchedule(@RequestParam Integer season, Model model){
        Iterable<Seasons> teams = seasonsRepository.findAllBySeasonId(season);
        Iterable<Venue> venues = venueRepository.findAll();
        Iterable<Matches> matches = matchesRepository.findBySeason(season);
        model.addAttribute("season",season);
        model.addAttribute("teams", teams);
        model.addAttribute("venues", venues);
        model.addAttribute("matches", matches);
        return "createSchedule";
    }

    @GetMapping(path="/createSchedule/{season}")
    public String createSchedule1(@PathVariable Integer season, Model model){
        Iterable<Seasons> teams = seasonsRepository.findAllBySeasonId(season);
        Iterable<Venue> venues = venueRepository.findAll();
        Iterable<Matches> matches = matchesRepository.findBySeason(season);
        model.addAttribute("season",season);
        model.addAttribute("teams", teams);
        model.addAttribute("venues", venues);
        model.addAttribute("matches", matches);
        return "createSchedule";
    }

//    @PostMapping("/delete")
//    public String removeMatch(@RequestParam Integer matchId, @RequestParam Integer season){
//        if( battingPerformanceRepository.findByMatchId(matchId).size()>0 || bowlingPerformanceRepository.findByMatchId(matchId).size()>0){
//
//            return "redirect:/matches/createSchedule/"+season.toString();
//        }
//        matchesRepository.deleteById(matchId);
//        return "redirect:/matches/createSchedule/"+season.toString();
//    }

    @PostMapping("/delete")
    public RedirectView removeMatch(@RequestParam Integer matchId, @RequestParam Integer season, RedirectAttributes redirectAttributes){
        if( battingPerformanceRepository.findByMatchMatchId(matchId).size()>0 || bowlingPerformanceRepository.findByMatchMatchId(matchId).size()>0){
            redirectAttributes.addFlashAttribute("errorMessage", "Match has already been played");
            return new RedirectView("/matches/createSchedule/"+season.toString());
        }
        matchesRepository.deleteById(matchId);
        return new RedirectView("/matches/createSchedule/"+season.toString());
    }

    @PostMapping(path="saveSchedule")
    public String saveSchedule(@RequestParam Integer team1, @RequestParam Integer team2,
                                              @RequestParam(name="date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                              @RequestParam Integer venue,
                                              @RequestParam Integer season){
        Matches m = new Matches();
        m.setVenue(venueRepository.findByVenueId(venue));
        m.setOpponent1(seasonsRepository.findByTeamIdAndSeason(team1,season));
        m.setOpponent2(seasonsRepository.findByTeamIdAndSeason(team2,season));
        m.setDate(date);
        matchesRepository.save(m);
        return "redirect:/matches/createSchedule/"+season.toString();
    }

    @GetMapping(path="updateDetails")
    public String updateDetails(@RequestParam Integer matchId, Model model){
        Matches match = matchesRepository.findByMatchId(matchId);
        model.addAttribute("match", match);
        return "updateMatchDetails";
    }

    @PostMapping(path="saveDetails")
    public String saveDetails(@RequestParam Integer matchId, @RequestParam Integer winningTeam, @RequestParam Integer winningToss, @RequestParam Integer battingFirst,
                                            @RequestParam Integer scoreTeam1, @RequestParam Integer scoreTeam2,
                                            @RequestParam Integer wicketsTeam1, @RequestParam Integer wicketsTeam2,
                                            @RequestParam Integer ballsPlayed1, @RequestParam Integer ballsPlayed2,
                                            Model model){
        //Integer m1 = Integer.parseInt(matchId);
        Matches m = matchesRepository.findByMatchId(matchId);
        m.addItems( winningTeam, winningToss, battingFirst, scoreTeam1, scoreTeam2,
                wicketsTeam1, wicketsTeam2, ballsPlayed1, ballsPlayed2);
        matchesRepository.save(m);
        Seasons opponent1 = matchesRepository.findByMatchId(matchId).getOpponent1();
        Seasons opponent2 = matchesRepository.findByMatchId(matchId).getOpponent2();
        opponent1.setTotalMatches(opponent1.getTotalMatches()+1);
        opponent2.setTotalMatches(opponent2.getTotalMatches()+1);
        if(winningTeam == 1){
            opponent1.setTotalWins(opponent1.getTotalWins()+1);
            opponent2.setTotalLosses(opponent2.getTotalLosses()+1);
            opponent1.setTotalPoints(opponent1.getTotalPoints()+2);
            System.out.println("opponent1 won");
        }
        else if(winningTeam == 2){
            opponent2.setTotalWins(opponent2.getTotalWins()+1);
            opponent1.setTotalLosses(opponent1.getTotalLosses()+1);
            opponent2.setTotalPoints(opponent2.getTotalPoints()+2);
            System.out.println("opponent2 won");
        }
        seasonsRepository.save(opponent1);
        seasonsRepository.save(opponent2);
        model.addAttribute("match", m);
        Set<Player> playersTeam1 = m.getOpponent1().getPlayers();
        Set<Player> playersTeam2 = m.getOpponent2().getPlayers();
        model.addAttribute("playersTeam1", playersTeam1);
        model.addAttribute("playersTeam2", playersTeam2);
        String path = "redirect:/matches/updateScoreCard/" + matchId.toString();
        return path;
    }

    @GetMapping(path="updateScoreCard/{matchId}")
    public String updateScoreCard(@PathVariable Integer matchId, Model model){
        Matches match = matchesRepository.findByMatchId(matchId);
        model.addAttribute("match", match);
        Set<Player> playersTeam1 = match.getOpponent1().getPlayers();
        Set<Player> playersTeam2 = match.getOpponent2().getPlayers();
        Iterable<BattingPerformance> battingTeam1 = battingPerformanceRepository.findByMatchAndTeam(match, playersTeam1);
        Iterable<BattingPerformance> battingTeam2 = battingPerformanceRepository.findByMatchAndTeam(match, playersTeam2);
        Iterable<BowlingPerformance> bowlingTeam1 = bowlingPerformanceRepository.findByMatchAndTeam(match, playersTeam1);
        Iterable<BowlingPerformance> bowlingTeam2 = bowlingPerformanceRepository.findByMatchAndTeam(match, playersTeam2);
        model.addAttribute("playersTeam1", playersTeam1);
        model.addAttribute("playersTeam2", playersTeam2);
        model.addAttribute("battingTeam1", battingTeam1);
        model.addAttribute("battingTeam2", battingTeam2);
        model.addAttribute("bowlingTeam1", bowlingTeam1);
        model.addAttribute("bowlingTeam2", bowlingTeam2);
        return "updateScoreCard";
    }

}
