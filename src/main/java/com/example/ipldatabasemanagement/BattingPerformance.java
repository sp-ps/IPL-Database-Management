package com.example.ipldatabasemanagement;

import javax.persistence.*;

@Entity
@IdClass(BattingPerformancePK.class)
public class BattingPerformance {

    @Id
    @ManyToOne
    @JoinColumn(name="playerId")
    private Player player;

    @Id
    @ManyToOne
    @JoinColumn(name="matchId")
    private Matches match;

    private Integer runs;
    private Integer balls;
    private Integer fours;
    private Integer sixes;
    private boolean dismissed;
    private String dismissalType;

    @ManyToOne
    @JoinColumn(name="dismissedBy")
    private Player bowler;

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getFours() {
        return fours;
    }

    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }


    public String getDismissalType() {
        return dismissalType;
    }

    public void setDismissalType(String dismissalType) {
        this.dismissalType = dismissalType;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public Player getBowler() {
        return bowler;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public Integer getBalls() {
        return balls;
    }

    public void setBalls(Integer balls) {
        this.balls = balls;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Matches getMatch() {
        return match;
    }

    public void setMatch(Matches match) {
        this.match = match;
    }
}
