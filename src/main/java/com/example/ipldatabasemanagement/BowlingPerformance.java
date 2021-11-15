package com.example.ipldatabasemanagement;

import javax.persistence.*;

@Entity
@IdClass(BowlingPerformancePK.class)
public class BowlingPerformance {
    @Id
    @ManyToOne
    @JoinColumn(name="playerId")
    private Player player;

    @Id
    @ManyToOne
    @JoinColumn(name="matchId")
    private Matches match;

    private Float overs;
    private Integer runs;
    private Integer wickets;
    private Integer maidens;

    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }

    public Float getOvers() {
        return overs;
    }

    public void setOvers(Float overs) {
        this.overs = overs;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Integer getMaidens() {
        return maidens;
    }

    public void setMaidens(Integer maidens) {
        this.maidens = maidens;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
