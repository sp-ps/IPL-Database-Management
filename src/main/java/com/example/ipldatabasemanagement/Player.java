package com.example.ipldatabasemanagement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer playerId;

    private String playerName;

    @ManyToMany(mappedBy = "players")
    private Set<Seasons> Seasons = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<BattingPerformance> battingPerformances = new HashSet<>();

    @OneToMany(mappedBy="bowler")
    private Set<BattingPerformance> dismissal = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<BowlingPerformance> bowlingPerformances;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    // find average of batting performance
    public double getBattingAverage(){
        double sum = 0;
        for(BattingPerformance bp : battingPerformances){
            sum += bp.getRuns();
        }
        return sum/battingPerformances.size();
    }
}
