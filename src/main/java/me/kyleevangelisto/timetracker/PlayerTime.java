package me.kyleevangelisto.timetracker;

import org.bukkit.OfflinePlayer;

public class PlayerTime{
    private long totalSessionTime;
    private OfflinePlayer offlinePlayer;


    public PlayerTime(long totalSessionTime, OfflinePlayer offlinePlayer){
        this.totalSessionTime = totalSessionTime;
        this.offlinePlayer = offlinePlayer;
    }

    public long getTotalSessionTime() {
        return totalSessionTime;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public void setTotalSessionTime(long totalSessionTime) {
        this.totalSessionTime = totalSessionTime;
    }

}
