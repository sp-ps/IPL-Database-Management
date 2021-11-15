package com.example.ipldatabasemanagement;

import java.io.Serializable;

public class SeasonsPK implements Serializable {
    private Integer team;
    private Integer season;

    public SeasonsPK( Integer team, Integer season) {
        this.season = season;
        this.team = team;
    }
    public SeasonsPK(){

    }

    public boolean equals(Object object) {
        if (object instanceof Seasons) {
            SeasonsPK pk = (SeasonsPK)object;
            return season == pk.season &&  team == pk.team;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return season + team;
    }

}
