package com.example.ipldatabasemanagement;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer matchId;

    private Date date;

    private Integer winningTeam;
    private Integer winningToss;
    private Integer battingFirst;

    private Integer scoreTeam1, wicketsTeam1, ballsPlayed1;
    private Integer scoreTeam2, wicketsTeam2, ballsPlayed2;



    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;


    @ManyToOne
    @JoinColumn(name="season2_Id")
    @JoinColumn(name="team1_Id")
    private Seasons opponent1;

    @ManyToOne
    @JoinColumn(name="season1_Id")
    @JoinColumn(name="team2_id")
    private Seasons opponent2;

    public Matches() {

    }

    public void addItems(Integer winningTeam, Integer winningToss, Integer battingFirst, Integer scoreTeam1, Integer scoreTeam2,
                    Integer wicketsTeam1, Integer wicketsTeam2, Integer ballsPlayed1, Integer ballsPlayed2)
    {
        this.winningTeam = winningTeam;
        this.winningToss = winningToss;
        this.battingFirst = battingFirst;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.ballsPlayed1 = ballsPlayed1;
        this.ballsPlayed2 = ballsPlayed2;
        this.wicketsTeam1 = wicketsTeam1;
        this.wicketsTeam2 = wicketsTeam2;
    }

    public Integer getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Integer winningTeam) {
        this.winningTeam = winningTeam;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }






    public Integer getWinningToss() {
        return winningToss;
    }

    public void setWinningToss(Integer winningToss) {
        this.winningToss = winningToss;
    }

    public Integer getBattingFirst() {
        return battingFirst;
    }

    public void setBattingFirst(Integer battingFirst) {
        this.battingFirst = battingFirst;
    }

    public Integer getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(Integer scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public Integer getWicketsTeam1() {
        return wicketsTeam1;
    }

    public void setWicketsTeam1(Integer wicketsTeam1) {
        this.wicketsTeam1 = wicketsTeam1;
    }

    public Integer getBallsPlayed1() {
        return ballsPlayed1;
    }

    public void setBallsPlayed1(Integer ballsPlayed1) {
        this.ballsPlayed1 = ballsPlayed1;
    }

    public Integer getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(Integer scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public Integer getWicketsTeam2() {
        return wicketsTeam2;
    }

    public void setWicketsTeam2(Integer wicketsTeam2) {
        this.wicketsTeam2 = wicketsTeam2;
    }

    public Integer getBallsPlayed2() {
        return ballsPlayed2;
    }

    public void setBallsPlayed2(Integer ballsPlayed2) {
        this.ballsPlayed2 = ballsPlayed2;
    }

    public Seasons getOpponent1() {
        return opponent1;
    }

    public void setOpponent1(Seasons opponent1) {
        this.opponent1 = opponent1;
    }

    public Seasons getOpponent2() {
        return opponent2;
    }

    public void setOpponent2(Seasons opponent2) {
        this.opponent2 = opponent2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
