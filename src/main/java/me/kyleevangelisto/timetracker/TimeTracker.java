package me.kyleevangelisto.timetracker;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimeTracker extends JavaPlugin {

    private static TimeTracker plugin;

    @Override
    public void onEnable() {
        PlayerManager.getInstance();
        plugin = this;

        getCommand("playtime").setExecutor(new PlayTimeCommand());
        getCommand("leaderboard").setExecutor(new LeaderboardCommand());

        getServer().getPluginManager().registerEvents(new Listeners(), this);
        saveDefaultConfig();

        PluginDescriptionFile pluginDescriptionFile = getDescription();
        getServer().getLogger().info("Booting " + pluginDescriptionFile.getName() + " version " +
                pluginDescriptionFile.getVersion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TimeTracker getPlugin(){
        return plugin;
    }
}
