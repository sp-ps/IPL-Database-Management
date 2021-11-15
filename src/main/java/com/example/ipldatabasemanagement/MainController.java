package com.example.ipldatabasemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.min;

@Controller
public class MainController {

    @Autowired
    private BattingPerformanceRepository battingPerformanceRepository;

    @Autowired
    private BowlingPerformanceRepository bowlingPerformanceRepository;

    @Autowired
    private MatchesRepository matchesRepository;

    @Autowired
    private SeasonsRepository seasonsRepository;

    @GetMapping(path = "/")
    public String home(Model model){
        return "newHome";
    }

    @GetMapping(path = "/home")
    public String home(){
        return "home";
    }

    @GetMapping(path="/stats")
    public String stats(@RequestParam Integer season, Model model){
        model.addAttribute("season", season);
        Iterable<Seasons> teams = seasonsRepository.findAllBySeasonIdAndPoints(season);
        model.addAttribute("teams", teams);
        Integer tournamentSixes = battingPerformanceRepository.countSixesBySeason(season);
        model.addAttribute("tournamentSixes", tournamentSixes);
        Integer tournamentFours = battingPerformanceRepository.countFoursBySeason(season);
        model.addAttribute("tournamentFours", tournamentFours);
        List<Tuple> totalRunsByPlayers = battingPerformanceRepository.countRunsByPlayer(season);
        List<Tuple> totalRunsByPlayers1 = totalRunsByPlayers.subList(0,min(totalRunsByPlayers.size(),6));
        List<Stats> totalRunsByPlayersList = new ArrayList<>();
        for(Tuple tuple : totalRunsByPlayers1){
            totalRunsByPlayersList.add(new Stats(tuple.get(0, Player.class), Integer.parseInt(tuple.get(1).toString())));
        }
        model.addAttribute("totalRunsByPlayers", totalRunsByPlayersList);
        List<Tuple> totalWicketsByPlayers = bowlingPerformanceRepository.countWicketsByPlayer(season);
        List<Tuple> totalWicketsByPlayers1 = totalWicketsByPlayers.subList(0,min(totalWicketsByPlayers.size(),5));
        List<Stats> totalWicketsByPlayersList = new ArrayList<>();
        for(Tuple tuple : totalWicketsByPlayers1){
            totalWicketsByPlayersList.add(new Stats(tuple.get(0, Player.class), Integer.parseInt(tuple.get(1).toString())));
        }
        model.addAttribute("totalWicketsByPlayers", totalWicketsByPlayersList);

        List<Tuple> totalSixesByPlayers = battingPerformanceRepository.countSixesByPlayer(season);
        List<Tuple> totalSixesByPlayers1 = totalSixesByPlayers.subList(0,min(totalSixesByPlayers.size(),5));
        List<Stats> totalSixesByPlayersList = new ArrayList<>();
        for(Tuple tuple : totalSixesByPlayers1){
            totalSixesByPlayersList.add(new Stats(tuple.get(0, Player.class), Integer.parseInt(tuple.get(1).toString())));
        }
        model.addAttribute("totalSixesByPlayers", totalSixesByPlayersList);

        List<Tuple> totalFoursByPlayers = battingPerformanceRepository.countFoursByPlayer(season);
        List<Tuple> totalFoursByPlayers1 = totalFoursByPlayers.subList(0,min(totalFoursByPlayers.size(),5));
        List<Stats> totalFoursByPlayersList = new ArrayList<>();
        for(Tuple tuple : totalFoursByPlayers1){
            totalFoursByPlayersList.add(new Stats(tuple.get(0, Player.class), Integer.parseInt(tuple.get(1).toString())));
        }
        model.addAttribute("totalFoursByPlayers", totalFoursByPlayersList);

        List<Tuple> highestRunsByPlayers = battingPerformanceRepository.highestRunsByPlayer(season);
        List<Tuple> highestRunsByPlayers1 = highestRunsByPlayers.subList(0,min(highestRunsByPlayers.size(),5));
        List<Stats> highestRunsByPlayersList = new ArrayList<>();
        for(Tuple tuple : highestRunsByPlayers1){
            highestRunsByPlayersList.add(new Stats(tuple.get(0, Player.class), Integer.parseInt(tuple.get(1).toString())));
        }
        model.addAttribute("highestRunsByPlayers", highestRunsByPlayersList);

        return "totalstats";
    }

    @GetMapping(path="/matches/{season}")
    public String matches(@PathVariable Integer season, Model model){
        model.addAttribute("season", season);
        Iterable<Matches> matches = matchesRepository.findBySeason(season);
        model.addAttribute("matches", matches);
        return "matches";
    }

    @GetMapping(path="/scoreCard")
    public String scoreCard(@RequestParam Integer matchId, Model model){
        Matches matches = matchesRepository.findById(matchId).get();
        model.addAttribute("match", matches);
        Set<Player> playersTeam1 = matches.getOpponent1().getPlayers();
        Set<Player> playersTeam2 = matches.getOpponent2().getPlayers();
        List<BattingPerformance> battingPerformances = battingPerformanceRepository.findByMatch(matches);
        List<BattingPerformance> battingPerformancesTeam1 = new ArrayList<>();
        List<BattingPerformance> battingPerformancesTeam2 = new ArrayList<>();
        for(BattingPerformance battingPerformance : battingPerformances){
            if(playersTeam1.contains(battingPerformance.getPlayer())){
                battingPerformancesTeam1.add(battingPerformance);
            }
            else{
                battingPerformancesTeam2.add(battingPerformance);
            }
        }
        model.addAttribute("battingPerformancesTeam1", battingPerformancesTeam1);
        model.addAttribute("battingPerformancesTeam2", battingPerformancesTeam2);
        List<BowlingPerformance> bowlingPerformances = bowlingPerformanceRepository.findByMatch(matches);
        List<BowlingPerformance> bowlingPerformancesTeam1 = new ArrayList<>();
        List<BowlingPerformance> bowlingPerformancesTeam2 = new ArrayList<>();
        for(BowlingPerformance bowlingPerformance : bowlingPerformances){
            if(playersTeam1.contains(bowlingPerformance.getPlayer())){
                bowlingPerformancesTeam1.add(bowlingPerformance);
            }
            else{
                bowlingPerformancesTeam2.add(bowlingPerformance);
            }
        }
        model.addAttribute("bowlingPerformancesTeam1", bowlingPerformancesTeam1);
        model.addAttribute("bowlingPerformancesTeam2", bowlingPerformancesTeam2);
        return "scoreCard";
    }

}
