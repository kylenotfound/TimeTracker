package me.kyleevangelisto.timetracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LeaderboardCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<PlayerTime> leaders = new ArrayList();
        ConfigurationSection cs = TimeTracker.getPlugin().getConfig().getConfigurationSection("players");
        for (String uuid : cs.getKeys(false)){
            long time = cs.getLong(uuid, 0l);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            if(offlinePlayer.isOnline()){
               time += PlayerManager.getInstance().getSessionTime(offlinePlayer.getUniqueId());
            }
            leaders.add(new PlayerTime(time, offlinePlayer)); //nice
        }
        List<PlayerTime> sortedTimes = leaders.stream().sorted(Comparator.comparingLong
                (PlayerTime::getTotalSessionTime).reversed()).collect(Collectors.toList());

        //allow for a custom leaderboard length up to the size of the list.
        int length = 10;
        if(args.length > 0) {
            try {
                length = Integer.parseInt(args[0]);
                if (length > sortedTimes.size()) {
                    sender.sendMessage(ChatColor.WHITE + "" + "The max amount is " + sortedTimes.size() + "\n" +
                            "Defaulting to " + sortedTimes.size());
                    length = sortedTimes.size();
                }
            } catch(NumberFormatException exception) {
                sender.sendMessage(ChatColor.WHITE + "" + "Bro that is not a number. Try again!");
                return false;
            }
        }

        //display the leaderboard to the chat
        for(int index = 0; index < sortedTimes.size() && index < length; index++ ){
            sender.sendMessage(ChatColor.GOLD + "" + (index + 1) + ") " +
                    sortedTimes.get(index).getOfflinePlayer().getName() + " " +
                    Duration.toHuman(Instant.now(),Instant.now().plusSeconds(sortedTimes.get(index).getTotalSessionTime())));
        }

      return false;
    }
}
