package me.kyleevangelisto.timetracker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class PlayTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("No no no");
            return true;
        }
        Player p = (Player) sender;
        long sessiontime = PlayerManager.getInstance().getSessionTime(p.getUniqueId());
        long totalTimePlayed = TimeTracker.getPlugin().getConfig().getLong("players."
                + p.getUniqueId().toString(), 0l);

        totalTimePlayed += sessiontime;
        sender.sendMessage(ChatColor.GOLD + "" + Duration.toHuman(Instant.now(),
                Instant.now().plusSeconds(totalTimePlayed)) + " Thanks for playing on my server!");

        return true;
    }
}
