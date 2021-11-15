package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path="/batting")
public class BattingPerformanceController {

    @Autowired
    private BattingPerformanceRepository battingPerformanceRepository;

    @Autowired
    private MatchesRepository matchesRepository;

    @Autowired
    private PlayerRepository playerRepository;


//    @PostMapping(path="/add")
//    public @ResponseBody String addBattingPerformance(@RequestParam Integer matchId, @RequestParam Integer playerId,
//                                                      @RequestParam Integer runs, @RequestParam Integer balls,
//                                                      @RequestParam Integer fours, @RequestParam Integer sixes,
//                                                      @RequestParam Integer dismissed,@RequestParam String dismissalType,
//                                                      @RequestParam Integer bowler) {
//        BattingPerformance bp = new BattingPerformance();
//        Matches m = matchesRepository.findById(matchId).get();
//        bp.setMatch(m);
//        Player p = playerRepository.findById(playerId).get();
//        bp.setPlayer(p);
//        bp.setRuns(runs);
//        bp.setBalls(balls);
//        bp.setFours(fours);
//        bp.setSixes(sixes);
//        if( dismissed == 1 ) {
//            bp.setDismissed(true);
//            bp.setDismissalType(dismissalType);
//            Player b = playerRepository.findById(bowler).get();
//            bp.setBowler(b);
//        }
//        else {
//            bp.setDismissed(false);
//        }
//        battingPerformanceRepository.save(bp);
//        return "Saved";
//    }

    @PostMapping(path = "/add")
    public RedirectView addBattingPerformance(@RequestParam Integer matchId, @RequestParam Integer playerId,
                                              @RequestParam Integer runs, @RequestParam Integer balls,
                                              @RequestParam Integer fours, @RequestParam Integer sixes,
                                              @RequestParam Integer dismissed, @RequestParam String dismissalType,
                                              @RequestParam Integer bowler, RedirectAttributes redirectAttributes) {
        if( battingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId) != null ) {
            redirectAttributes.addFlashAttribute("errorMessage", "Player already has a batting performance for this match");
            String path = "/matches/updateScoreCard/"+matchId.toString();
            return new RedirectView(path, true);
        }

        BattingPerformance bp = new BattingPerformance();
        Matches m = matchesRepository.findById(matchId).get();
        bp.setMatch(m);
        Player p = playerRepository.findById(playerId).get();
        bp.setPlayer(p);
        bp.setRuns(runs);
        bp.setBalls(balls);
        bp.setFours(fours);
        bp.setSixes(sixes);
        if( dismissed == 1 ) {
            bp.setDismissed(true);
            bp.setDismissalType(dismissalType);
            Player b = playerRepository.findById(bowler).get();
            bp.setBowler(b);
        }
        else {
            bp.setDismissed(false);
        }
        battingPerformanceRepository.save(bp);
        String userMessage = "Batting Performance of "+ playerRepository.findById(playerId).get().getPlayerName() + " added successfully";
        redirectAttributes.addFlashAttribute("userMessage", userMessage);
        String path = "/matches/updateScoreCard/"+matchId.toString();
        return new RedirectView(path, true);
    }

    @PostMapping(path="/delete")
    public RedirectView delete(@RequestParam Integer matchId, @RequestParam Integer playerId, RedirectAttributes redirectAttributes){
        if(battingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId) == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Player does not exist in scorecard");
            String path = "/matches/updateScoreCard/" + matchId.toString();
            RedirectView rv = new RedirectView(path, true);
            return rv;
        }
        BattingPerformance bp = battingPerformanceRepository.findByMatchIdAndPlayerId(matchId, playerId);
        battingPerformanceRepository.delete(bp);
        String message = "Batting Performance of " + playerRepository.findById(playerId).get().getPlayerName() + " deleted successfully";
        String path = "/matches/updateScoreCard/" + matchId.toString();
        RedirectView rv = new RedirectView(path, true);
        redirectAttributes.addFlashAttribute("userMessage", message);
        return rv;
    }



}

