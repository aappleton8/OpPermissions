package me.OpPermissions;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class OpPermissionsCommands implements CommandExecutor{
	public static OpPermissionsMainClass plugin; 
	public OpPermissionsCommands(OpPermissionsMainClass instance) {
		plugin = instance; 
	}
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (args.length != 2) {
			return false; 
		}
		else if (args[0].equalsIgnoreCase("add")) {
			if (s.hasPermission("oppermissions.add") || (s instanceof ConsoleCommandSender)) {
				@SuppressWarnings("deprecation")
				Player p = Bukkit.getPlayer(args[1]); 
				if (p != null) {
					List<String> ops = plugin.getConfig().getStringList("ops"); 
					plugin.getConfig().set("ops", null); 
					ops.add(args[1]); 
					plugin.getConfig().set("ops", ops); 
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "op " + args[1]); 
					s.sendMessage(args[1] + " added to the permenant ops list "); 
				}
				else {
					s.sendMessage(ChatColor.RED + "Could not obtain player "); 
				}
			}
			else {
				plugin.noPermission(s); 
			}
		}
		else if (args[0].equalsIgnoreCase("remove")) {
			if (s.hasPermission("oppermissions.remove") || (s instanceof ConsoleCommandSender)) {
				List<String> ops = plugin.getConfig().getStringList("ops"); 
				plugin.getConfig().set("ops", null); 
				ops.remove(args[1]); 
				plugin.getConfig().set("ops", ops); 
				s.sendMessage(args[1] + " removed from the permenant ops list "); 
			}
			else {
				plugin.noPermission(s); 
			}
		}
		else if (args[0].equalsIgnoreCase("list")) {
			if (s.hasPermission("oppermissions.list") || (s instanceof ConsoleCommandSender)) {
				if (args[1].equalsIgnoreCase("list")) {
					s.sendMessage(plugin.getConfig().getString("ops")); 
				}
				else {
					Boolean isThere = false; 
					for (String i : plugin.getConfig().getStringList("ops")) {
						if (i.equalsIgnoreCase(args[1])) {
							isThere = true; 
						}
					}
					if (isThere == true) {
						s.sendMessage(args[1] + " is a permenant op "); 
					}
					else {
						s.sendMessage(args[1] + " is not a permenant op "); 
					}
				}
			}
			else {
				plugin.noPermission(s); 
			}
		}
		else if (args[0].equalsIgnoreCase("config")) {
			if (args[1].equalsIgnoreCase("save")) {
				if (s.hasPermission("oppermissions.save") || (s instanceof ConsoleCommandSender)) {
					plugin.saveConfig(); 
					Bukkit.broadcastMessage("OpPermissions configuration saved "); 
				}
				else {
					plugin.noPermission(s); 
				}
			}
			else if (args[1].equalsIgnoreCase("reload")) {
				if (s.hasPermission("oppermissions.reload") || (s instanceof ConsoleCommandSender)) {
					plugin.reloadConfig(); 
					Bukkit.broadcastMessage("OpPermissions configuration reloaded "); 
				}
				else {
					plugin.noPermission(s); 
				}
			}
			else {
				return false; 
			}
		}
		else {
			return false; 
		}
		plugin.saveConfig(); 
		return true;
	}
}
