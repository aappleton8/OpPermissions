package me.OpPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class OpPermissionsCommands implements CommandExecutor {
	public static OpPermissionsMainClass plugin; 
	public OpPermissionsCommands(OpPermissionsMainClass instance) {
		plugin = instance; 
	}
	
	private void helpCommand(CommandSender s) {
		helpCommand(s, false); 
	}
	
	private void helpCommand(CommandSender s, Boolean showAll) {
		if (s instanceof ConsoleCommandSender) {
			showAll = true; 
		}
		s.sendMessage(ChatColor.DARK_RED + "The commands the the " + plugin.getName() + " plugin are: "); 
		s.sendMessage(ChatColor.RED + "/oppermissions - Show this message "); 
		s.sendMessage(ChatColor.RED + "/opset help - Show this message "); 
		if (s.hasPermission("oppermissions.showallhelp") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset help all - Show help for all " + plugin.getName() + " commands "); 
		}
		s.sendMessage(ChatColor.RED + "/opset version - Show the plugin version "); 
		if (s.hasPermission("oppermissions.read.list") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset list - List the permanent ops "); 
		}
		if (s.hasPermission("oppermissions.read.check") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset check <username> - Check if someone is a permanent op "); 
		}
		if (s.hasPermission("oppermissions.add") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset add <username> - Add a permanent op "); 
		}
		if (s.hasPermission("oppermissions.remove") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset remove <username> - Remove a permanent op "); 
		}
		if (s.hasPermission("oppermissions.config.save") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset config save - Save the config file "); 
		}
		if (s.hasPermission("oppermissions.config.reload") || showAll) { 
			s.sendMessage(ChatColor.RED + "/opset config reload - Reload the config file "); 
		}
		if (s.hasPermission("oppermissions.config.set.opscanop") || s.hasPermission("oppermissions.config.set.allowrequests") || showAll) {
			s.sendMessage(ChatColor.RED + "/opset config set <field> <value> - Set the value of the 'opscanop' and 'allowrequests' fields of the config file "); 
		}
		if (s.hasPermission("oppermissions.oplist.online") || s.hasPermission("oppermissions.oplist.offline") || showAll) {
			s.sendMessage(ChatColor.RED + "/oplist - List all the ops (variable output depending on permissions) "); 
		}
		if (s.hasPermission("oppermissions.oplist.online") && s.hasPermission("oppermissions.oplist.offline") || showAll) {
			s.sendMessage(ChatColor.RED + "/oplist both - List all the ops (both online and offline) "); 
		}
		if (s.hasPermission("oppermissions.oplist.online") || showAll) {
			s.sendMessage(ChatColor.RED + "/oplist online - List all the online ops "); 
		}
		if (s.hasPermission("oppermissions.oplist.offline") || showAll) {
			s.sendMessage(ChatColor.RED + "/oplist offline - List all the offline ops "); 
		}
		if (plugin.getConfig().getString("allowrequests") != "no" && (s.hasPermission("oppermissions.oprequest.send") || showAll)) {
			s.sendMessage(ChatColor.RED + "/oprequest - send a message request to the ops "); 
		}
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (l.equalsIgnoreCase("opset")) {
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set")) {
					if (args[2].equalsIgnoreCase("opscanop")) {
						if (s.hasPermission("oppermissions.config.set.opscanop") || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("op") || args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("no") || args[3].equalsIgnoreCase("default")) {
								plugin.getConfig().set("opscanop", args[3]); 
								Bukkit.broadcast(ChatColor.GREEN + "The " + plugin.getName() + " config has been updated ", "oppermissions.seepluginmessages"); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the 'opscanop' field must be either 'op', 'permission', 'default' or 'no'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("allowrequests")) {
						if (s.hasPermission("oppermissions.config.set.allowrequests") || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("op") || args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("no")) {
								plugin.getConfig().set("allowrequests", args[3]); 
								Bukkit.broadcast(ChatColor.GREEN + "The " + plugin.getName() + " config has been updated ", "oppermissions.seepluginmessages"); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the 'allowrequests' field must be either 'op', 'permission' or 'no'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
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
						if (s.hasPermission("oppermissions.config.save") || (s instanceof ConsoleCommandSender)) {
							plugin.saveConfig(); 
							Bukkit.broadcast(plugin.getName() + " configuration saved ", "oppermissions.seepluginmessages"); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[1].equalsIgnoreCase("reload")) {
						if (s.hasPermission("oppermissions.config.reload") || (s instanceof ConsoleCommandSender)) {
							plugin.reloadConfig(); 
							Bukkit.broadcast(plugin.getName() + " configuration reloaded ", "oppermissions.seepluginmessages"); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else {
						return false; 
					}
				}
				else if (args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("all")) {
					if (s.hasPermission("oppermissions.showallhelp") || (s instanceof ConsoleCommandSender)) {
						helpCommand(s, true); 
					}
					else {
						plugin.noPermission(s); 
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
				else if (args[0].equalsIgnoreCase("version")) {
					s.sendMessage(plugin.getDescription().getName() + " is version " + plugin.getDescription().getVersion()); 
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
			else {
				return false; 
			}
		}
		else if (l.equalsIgnoreCase("oplist")) {
			if (args.length < 2) {
				if ((s.hasPermission("oppermissions.oplist.online") == false) && (s.hasPermission("oppermissions.oplist.offline") == false) && (!(s instanceof ConsoleCommandSender))) {
					plugin.noPermission(s); 
				}
				else {
					List<String> argsList = new ArrayList<>(Arrays.asList(args)); 
					if (argsList.size() == 0) {
						argsList.add("online"); 
						argsList.add("offline"); 
					}
					else if (!(args[0].equalsIgnoreCase("online") || args[0].equalsIgnoreCase("offline") || args[0].equalsIgnoreCase("both"))) {
						return false; 
					}
					Set<OfflinePlayer> ops = Bukkit.getOperators(); 
					String onlineOps = null; 
					String offlineOps = null; 
					int onlineOpCount = 0; 
					int offlineOpCount = 0; 
					if (ops.isEmpty() == false) {
						onlineOps = ": "; 
						offlineOps = ": "; 
						for (OfflinePlayer i : ops) {
							if (i.isOnline() == true) {
								onlineOpCount += 1; 
								onlineOps += i.getName() + ", "; 
							}
							else {
								offlineOpCount += 1; 
								offlineOps += i.getName() + ", "; 
							}
						}
						onlineOps = onlineOps.substring(0, onlineOps.length() - 2); 
						offlineOps = offlineOps.substring(0, offlineOps.length() - 2); 
					}
					if ((!(s instanceof ConsoleCommandSender)) && (argsList.contains("both")) && (!(s.hasPermission("oppermissions.oplist.online") && s.hasPermission("oppermissions.oplist.offline")))) {
						plugin.noPermission(s); 
					}
					else if ((!(s instanceof ConsoleCommandSender)) && (args.length == 1) && (!(s.hasPermission("oppermissions.oplist." + args[0])))) {
						plugin.noPermission(s); 
					}
					else {
						if (argsList.contains("online") || argsList.contains("both")) {
							if (onlineOpCount == 0) {
								s.sendMessage("There are no ops online "); 
							}
							else {
								s.sendMessage("The " + Integer.toString(onlineOpCount) + " ops online are" + onlineOps); 
							}
						}
						if (argsList.contains("offline") || argsList.contains("both")) {
							if (offlineOpCount == 0) {
								s.sendMessage("There are no ops offline "); 
							}
							else {
								s.sendMessage("The " + Integer.toString(offlineOpCount) + " ops offline are" + offlineOps); 
							}
						}
					}
				}
			}
			else {
				return false; 
			}
		}
		else if (l.equalsIgnoreCase("oprequest")) {
			if (args.length == 0) {
				return false; 
			}
			else {
				if (s.hasPermission("oppermissions.oprequest.send") || (s instanceof ConsoleCommandSender)) {
					if (plugin.getConfig().getString("allowrequests") != "no") {
						String message = String.join(" ", args); 
						message = s.getName() + " asks: " + message; 
						if (plugin.getConfig().getString("allowrequests").equalsIgnoreCase("permission")) {
							Bukkit.broadcast(message, "oppermissions.oprequest.receive"); 
							s.sendMessage(message); 
						}
						else if (plugin.getConfig().getString("allowrequests").equalsIgnoreCase("op")) {
							Bukkit.broadcast(message, Server.BROADCAST_CHANNEL_ADMINISTRATIVE); 
							s.sendMessage(message); 
						}
						else {
							s.sendMessage(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value "); 
							plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the allowrequests field (it should be 'op', 'permission' or 'no'"); 
						}
					}
					else {
						s.sendMessage("This feature has been disabled "); 
					}
				}
				else {
					plugin.noPermission(s); 
				}
			}
		}
		else {
			return false; 
		}
		plugin.saveConfig(); 
		return true;
	}
}
