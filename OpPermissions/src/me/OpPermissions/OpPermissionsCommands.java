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
	
	private void helpCommand(CommandSender s) {
		s.sendMessage(ChatColor.DARK_RED + "The commands the the OpPermissions plugin are: "); 
		s.sendMessage(ChatColor.RED + "/oppermissions - Show this message "); 
		s.sendMessage(ChatColor.RED + "/opset help - Show this message "); 
		s.sendMessage(ChatColor.RED + "/opset list - List the permanent ops "); 
		s.sendMessage(ChatColor.RED + "/opset check <username> - Check if someone is a permanent op "); 
		s.sendMessage(ChatColor.RED + "/opset add <username> - Add a permanent op "); 
		s.sendMessage(ChatColor.RED + "/opset remove <username> - Remove a permanent op "); 
		s.sendMessage(ChatColor.RED + "/opset config save - Save the config file "); 
		s.sendMessage(ChatColor.RED + "/opset config reload - Reload the config file "); 
		s.sendMessage(ChatColor.RED + "/opset config set <value> - Set the value of the 'opscanop' field of the config file (requires a config reload) "); 
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (l.equalsIgnoreCase("opset")) {
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set")) {
					if (s.hasPermission("oppermissions.setcanop")) {
						if (args[2].equalsIgnoreCase("op") || args[2].equalsIgnoreCase("permission") || args[2].equalsIgnoreCase("no")) {
							plugin.getConfig().set("opscanop", args[2]); 
						}
						else {
							s.sendMessage(ChatColor.RED + "The value of the 'opscanop' field must be either 'op', 'permission' or 'no'"); 
						}
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else {
					return false; 
				}
			}
			else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("add")) {
					if (s.hasPermission("oppermissions.add") || (s instanceof ConsoleCommandSender)) {
						@SuppressWarnings("deprecation")
						Player p = Bukkit.getPlayer(args[1]); 
						if (p != null) {
							List<String> ops = plugin.getConfig().getStringList("ops"); 
							Boolean alreadyOnList = false; 
							for (String i : ops) {
								if (i.equalsIgnoreCase(args[1])) {
									alreadyOnList = true; 
									break; 
								}
							}
							if (alreadyOnList == false) {
								plugin.getConfig().set("ops", null); 
								ops.add(args[1]); 
								plugin.getConfig().set("ops", ops); 
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "op " + args[1]); 
								s.sendMessage(args[1] + " added to the permenant ops list "); 
							}
							else {
								s.sendMessage(ChatColor.RED + args[1] + " was already on the list "); 
							}
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
				else if (args[0].equalsIgnoreCase("check")) {
					if (s.hasPermission("oppermissions.check") || (s instanceof ConsoleCommandSender)) {
						String playerName = args[1]; 
						Boolean isThere = false; 
						for (String i : plugin.getConfig().getStringList("ops")) {
							if (i.equalsIgnoreCase(playerName)) {
								isThere = true; 
								break; 
							}
						}
						if (isThere == true) {
							s.sendMessage(args[1] + " is a permenant op "); 
						}
						else {
							s.sendMessage(args[1] + " is not a permenant op "); 
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
			}
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					if (s.hasPermission("oppermissions.list") || (s instanceof ConsoleCommandSender)) {
						s.sendMessage(plugin.getConfig().getString("ops")); 
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else if (args[0].equalsIgnoreCase("help")) {
					helpCommand(s); 
				}
				else {
					return false; 
				}
			}
			else {
				return false; 
			}
		}
		else if (l.equalsIgnoreCase("oppermissions")) {
			if (args.length == 0) {
				helpCommand(s); 
			}
		}
		else {
			return false; 
		}
		plugin.saveConfig(); 
		return true;
	}
}
