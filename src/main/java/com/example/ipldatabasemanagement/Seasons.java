package com.example.ipldatabasemanagement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@IdClass(SeasonsPK.class)
public class Seasons {
    @Id
    private Integer season;

    @Id
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany
    @JoinTable (
            name = "PlaysFor",
            joinColumns = {@JoinColumn(name="seasonId"), @JoinColumn(name="teamId")},
            inverseJoinColumns = {@JoinColumn(name="playerId")}
    )
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "opponent1")
    private Set<Matches> homeMatches = new HashSet<>();

    @OneToMany(mappedBy = "opponent2")
    private Set<Matches> awayMatches = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "Coaches",
            joinColumns = {@JoinColumn(name="seasonId"), @JoinColumn(name="teamId")}
    )
    @Column(name="coaches")
    private List<String> coaches;

    @ElementCollection
    @CollectionTable(
            name= "Owners",
            joinColumns = {@JoinColumn(name="seasonId"), @JoinColumn(name="teamId")}
    )
    @Column(name = "owners")

    private List<String> owners;

    private Integer totalMatches;
    private Integer totalWins;
    private Integer totalLosses;
    private Integer totalDraws;
    private Integer totalPoints;


    public Seasons(Integer season, Team team)
    {
        this.season = season;
        this.team = team;
    }


    public Seasons() {
        this.totalWins = 0;
        this.totalLosses = 0;
        this.totalDraws = 0;
        this.totalPoints = 0;
        this.totalMatches = 0;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public List<String> getCoaches() {
        return coaches;
    }

    public void setCoaches(String coach) {
        this.coaches.add(coach);
    }


    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(String owner) {
        this.owners.add(owner);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Player player) {
        this.players.add(player);
    }

    public Integer getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(Integer totalMatches) {
        this.totalMatches = totalMatches;
    }

    public Integer getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(Integer totalWins) {
        this.totalWins = totalWins;
    }

    public Integer getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(Integer totalLosses) {
        this.totalLosses = totalLosses;
    }

    public Integer getTotalDraws() {
        return totalDraws;
    }

    public void setTotalDraws(Integer totalDraws) {
        this.totalDraws = totalDraws;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}
