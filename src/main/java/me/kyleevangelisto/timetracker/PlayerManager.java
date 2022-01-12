package me.kyleevangelisto.timetracker;


import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private static PlayerManager instance;

    public static PlayerManager getInstance(){
        if (instance == null){
            instance = new PlayerManager();
        }
        return instance;
    }

    HashMap<UUID, Long> players;

    private PlayerManager(){
        players = new HashMap<>();
    }

    public void trackNewPlayer(UUID uuid){
        long now = (new Date()).getTime() / 1000;
        players.put(uuid, now);
    }
    public long getSessionTime(UUID uuid){
        long now = (new Date().getTime() / 1000);
        long total = now - players.get(uuid);
        return total;
    }
    public long onPlayerLeave(UUID uuid){
        long sessionTime = getSessionTime(uuid);
        players.remove(uuid);
        long totalTimePlayed = TimeTracker.getPlugin().getConfig().getLong("players." + uuid.toString(), 0l);
        totalTimePlayed += sessionTime;
        TimeTracker.getPlugin().getConfig().set("players." + uuid.toString(), totalTimePlayed);
        TimeTracker.getPlugin().saveConfig();
        return sessionTime;
    }

}
