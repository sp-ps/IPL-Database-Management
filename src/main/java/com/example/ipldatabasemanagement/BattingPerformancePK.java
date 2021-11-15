package com.example.ipldatabasemanagement;

import java.io.Serializable;

public class BattingPerformancePK implements Serializable {

    private Integer match;
    private Integer player;

    public BattingPerformancePK(Integer match, Integer player) {
        this.match = match;
        this.player = player;
    }

    public BattingPerformancePK(){

    }

    public boolean equals(Object object) {
        if (object instanceof Seasons) {
            BattingPerformancePK pk = (BattingPerformancePK)object;
            return match == pk.match &&  player == pk.player;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return match + player;
    }

}
