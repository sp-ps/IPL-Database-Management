package com.example.ipldatabasemanagement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer teamId;

    private String teamName;

    private String petName;

    @OneToMany(mappedBy = "team")
    private Set<Seasons> seasons = new HashSet<>();


    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}
