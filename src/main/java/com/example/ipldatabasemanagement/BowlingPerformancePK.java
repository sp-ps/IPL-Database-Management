package com.example.ipldatabasemanagement;

import java.io.Serializable;

public class BowlingPerformancePK implements Serializable {

    private Integer match;
    private Integer player;

    public BowlingPerformancePK(Integer match, Integer player) {
        this.match = match;
        this.player = player;
    }

    public BowlingPerformancePK(){

    }

    public boolean equals(Object object) {
        if (object instanceof Seasons) {
            BowlingPerformancePK pk = (BowlingPerformancePK)object;
            return match == pk.match &&  player == pk.player;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return match + player;
    }

}
