package me.liliowy.lilairdrops.commands;

import me.liliowy.lilairdrops.LilAirdrops;
import me.liliowy.lilairdrops.storage.Language;
import me.liliowy.lilairdrops.util.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandLilAirdrops implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args){
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")){
            LilAirdrops.getInstance().init();
            sender.sendMessage(Formatting.format(Language.RELOADED, true));
        } else {
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length == 0){
                    if (player.hasPermission("lilairdrops.admin")) {
                        LilAirdrops.getInstance().getGuiHandler().getMainInventory().open(player);
                    } else {
                        player.sendMessage(Formatting.format(Language.COMMAND_NO_PERMISSION, true));
                    }
                }
            } else {
                sender.sendMessage(Formatting.format(Language.PLAYER_ONLY, true));
            }
        }

        return false;
    }
}
