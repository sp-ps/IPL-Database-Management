package com.example.ipldatabasemanagement;

public class Stats {
    private Player player;
    private Integer stats;

    public Stats(Player player, Integer stats) {
        this.player = player;
        this.stats = stats;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getStats() {
        return stats;
    }

    public void setStats(Integer stats) {
        this.stats = stats;
    }
}
