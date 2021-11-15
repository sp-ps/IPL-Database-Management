package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping(path = "/bowling")
public class BowlingPerformanceController {

    @Autowired
    private BowlingPerformanceRepository bowlingPerformanceRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchesRepository matchesRepository;

//    @RequestMapping(path = "/add")
//    public @ResponseBody String add(@RequestParam Integer matchId, @RequestParam Integer playerId, @RequestParam Float overs,
//               @RequestParam Integer runs, @RequestParam Integer wickets,
//               @RequestParam Integer maidens){
//        Matches match = matchesRepository.findById(matchId).get();
//        Player player = playerRepository.findById(playerId).get();
//        BowlingPerformance bp = new BowlingPerformance();
//        bp.setMatch(match);
//        bp.setPlayer(player);
//        bp.setOvers(overs);
//        bp.setRuns(runs);
//        bp.setWickets(wickets);
//        bp.setMaidens(maidens);
//        bowlingPerformanceRepository.save(bp);
//        return "Saved!";
//    }

    @GetMapping(path="/add")
    public RedirectView add(@RequestParam Integer matchId, @RequestParam Integer playerId, @RequestParam Float overs,
                                    @RequestParam Integer runs, @RequestParam Integer wickets,
                                    @RequestParam Integer maidens, RedirectAttributes redirectAttributes){
        if(bowlingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId) != null){
            redirectAttributes.addFlashAttribute("errorMessage", "Player already exists in scorecard");
            String path = "/matches/updateScoreCard/" + matchId.toString();
            RedirectView rv = new RedirectView(path, true);
            return rv;
        }

        Matches match = matchesRepository.findById(matchId).get();
        Player player = playerRepository.findById(playerId).get();
        BowlingPerformance bp = new BowlingPerformance();
        bp.setMatch(match);
        bp.setPlayer(player);
        bp.setOvers(overs);
        bp.setRuns(runs);
        bp.setWickets(wickets);
        bp.setMaidens(maidens);
        bowlingPerformanceRepository.save(bp);
        String message = "Bowling Performance of " + playerRepository.findById(playerId).get().getPlayerName() + " added successfully";
        String path = "/matches/updateScoreCard/" + matchId.toString();
        RedirectView rv = new RedirectView(path, true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return rv;
    }

    @PostMapping(path="/delete")
    public RedirectView delete(@RequestParam Integer matchId, @RequestParam Integer playerId, RedirectAttributes redirectAttributes){
        if(bowlingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId) == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Player does not exist in scorecard");
            String path = "/matches/updateScoreCard/" + matchId.toString();
            RedirectView rv = new RedirectView(path, true);
            return rv;
        }
        BowlingPerformance bp = bowlingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId);
        bowlingPerformanceRepository.delete(bp);
        String message = "Bowling Performance of " + playerRepository.findById(playerId).get().getPlayerName() + " deleted successfully";
        String path = "/matches/updateScoreCard/" + matchId.toString();
        RedirectView rv = new RedirectView(path, true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return rv;
    }

}
