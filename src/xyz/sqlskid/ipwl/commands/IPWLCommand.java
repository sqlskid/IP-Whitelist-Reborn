package xyz.sqlskid.ipwl.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import xyz.sqlskid.ipwl.IPWhitelist;

import java.io.Console;
import java.util.Objects;
import java.util.UUID;

public class IPWLCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        if(commandSender instanceof ConsoleCommandSender) {
            if(args.length < 1){
                commandSender.sendMessage("§a§lThis server is secured by IP Whitelist Reborn!");
                return true;
            }
            switch(args[0].toLowerCase()){
                case "add":
                    if(args.length == 2){
                        if(!IPWhitelist.instance.ipDatabase.containsKey(Bukkit.getPlayerUniqueId(args[1]))) {
                            IPWhitelist.instance.ipDatabase.put(Bukkit.getPlayerUniqueId(args[1]), "");
                            commandSender.sendMessage(IPWhitelist.instance.prefix + "§a " + args[1] + " has been added to IP Whitelist!");
                        }else {
                            commandSender.sendMessage(IPWhitelist.instance.prefix + "§c " + args[1] + " is already on IP Whitelist!");
                        }
                    }

                    break;
                case "remove":
                    if(args.length == 2){
                        if(IPWhitelist.instance.ipDatabase.containsKey(Bukkit.getPlayerUniqueId(args[1]))) {
                            IPWhitelist.instance.ipDatabase.remove(Bukkit.getPlayerUniqueId(args[1]));
                            commandSender.sendMessage(IPWhitelist.instance.prefix + "§a " + args[1] + " has been removed from IP Whitelist!");
                        }
                        else{
                            commandSender.sendMessage(IPWhitelist.instance.prefix + "§c " + args[1] + " is not on IP Whitelist!");
                        }
                    }
                    break;
                case "list":
                    commandSender.sendMessage("List of players that are on IP Whitelist:");
                    for(UUID uuid: IPWhitelist.instance.ipDatabase.keySet()){
                        commandSender.sendMessage(Objects.requireNonNull(Bukkit.getOfflinePlayer(uuid).getName()));
                    }
                    break;
                default:
                    commandSender.sendMessage("§aIP Whitelist commands");
                    commandSender.sendMessage("§7---------------------");
                    commandSender.sendMessage("§a/" + string.toLowerCase() + " add <Username>");
                    commandSender.sendMessage("§a/" + string.toLowerCase() + " remove <Username>");
                    commandSender.sendMessage("§a/" + string.toLowerCase() + " lists");
                    commandSender.sendMessage("§7---------------------");
                    break;
            }
            return true;
        }
        else
        {
            commandSender.sendMessage("§a§lThis server is secured by IP Whitelist Reborn!");
            return false;
        }
    }
}
