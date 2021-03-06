package me.OpPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

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
		helpCommand(s, false, 1); 
	}
	
	private void helpCommand(CommandSender s, Boolean showAll, int page) {
		if (s instanceof ConsoleCommandSender) {
			showAll = true; 
		}
		if (page == 1) {
			s.sendMessage(ChatColor.DARK_AQUA + "The commands for the " + ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " plugin are: "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/oppermissions " + ChatColor.AQUA + "- Show help messages "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset help [1|2|3] " + ChatColor.AQUA + "- Show help messages "); 
			if (s.hasPermission("oppermissions.showallhelp") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset help all [1|2|3] " + ChatColor.AQUA + "- Show help for all " + plugin.getName() + " commands "); 
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset help config [1|2]" + ChatColor.AQUA + "- Show help for config fields " + plugin.getName() + " commands "); 
			}
			else if (s.hasPermission("oppermissions.config.seedetailedsethelp") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset help config " + ChatColor.AQUA + "- Show help for config fields " + plugin.getName() + " commands ");
			}
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset version " + ChatColor.AQUA + "- Show the plugin version "); 
			if (s.hasPermission("oppermissions.read.list") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset list " + ChatColor.AQUA + "- List the permanent ops "); 
			}
			if (s.hasPermission("oppermissions.read.check") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset check <username> " + ChatColor.AQUA + "- Check a permanent op status "); 
			}
			if (s.hasPermission("oppermissions.add") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset add <username> " + ChatColor.AQUA + "- Add a permanent op "); 
			}
			if (s.hasPermission("oppermissions.remove") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset remove <username> " + ChatColor.AQUA + "- Remove a permanent op "); 
			}
			if (showAll) {
				s.sendMessage(ChatColor.DARK_AQUA + "More help commands are on page 2 (" + ChatColor.DARK_PURPLE + "/opset help all 2" + ChatColor.DARK_AQUA + ") "); 
			}
			else {
				s.sendMessage(ChatColor.DARK_AQUA + "More help commands are on page 2 (" + ChatColor.DARK_PURPLE + "/opset help 2" + ChatColor.DARK_AQUA + ") "); 
			}
		}
		else if (page == 2) {
			s.sendMessage(ChatColor.DARK_AQUA + "The commands for the " + ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " plugin are: "); 
			if (s.hasPermission("oppermissions.config.save") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset config save " + ChatColor.AQUA + "- Save the config file "); 
			}
			if (s.hasPermission("oppermissions.config.reload") || showAll) { 
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset config reload " + ChatColor.AQUA + "- Reload the config file "); 
			}
			if (s.hasPermission("oppermissions.config.seedetailedsethelp") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set <field> <value> " + ChatColor.AQUA + "- Set the value of specified config file field "); 
			}
			if (s.hasPermission("oppermissions.config.verifylist") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opset config verifylist " + ChatColor.AQUA + "- Update player names and UUIDs "); 
			}
			if (s.hasPermission("oppermissions.oplist.online") || s.hasPermission("oppermissions.oplist.offline") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/oplist " + ChatColor.AQUA + "- List all the ops (depending on permissions) "); 
			}
			if (s.hasPermission("oppermissions.oplist.online") && s.hasPermission("oppermissions.oplist.offline") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/oplist both " + ChatColor.AQUA + "- List all the ops (both online and offline) "); 
			}
			if (s.hasPermission("oppermissions.oplist.online") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/oplist online " + ChatColor.AQUA + "- List all the online ops "); 
			}
			if (s.hasPermission("oppermissions.oplist.offline") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/oplist offline " + ChatColor.AQUA + "- List all the offline ops "); 
			}
			if ((plugin.getConfig().getString("allowrequests") != "no") && (plugin.getConfig().getString("allowrequests") != "false") && (s.hasPermission("oppermissions.oprequest.send") || showAll)) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/oprequest " + ChatColor.AQUA + "- Send a message request to the ops "); 
			}
		}
		else {
			s.sendMessage(ChatColor.DARK_AQUA + "The commands for the " + ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " plugin are: "); 
			if (s.hasPermission("oppermissions.commands.add") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opcommands add <command> " + ChatColor.AQUA + "- Block a new command ");
			}
			if (s.hasPermission("oppermissions.commands.remove") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opcommands remove <command> " + ChatColor.AQUA + "- Unblock a command ");
			}
			if (s.hasPermission("oppermissions.commands.check") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opcommands check <command> " + ChatColor.AQUA + "- Check to see if a command is blocked ");
			}
			if (s.hasPermission("oppermissions.commands.list") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opcommands list " + ChatColor.AQUA + "- List the blocked commands ");
			}
			if (s.hasPermission("oppermissions.opbancommands.add") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opbancommands add <command> " + ChatColor.AQUA + "- Add a new watched ban command ");
			}
			if (s.hasPermission("oppermissions.opbancommands.remove") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opbancommands remove <command> " + ChatColor.AQUA + "- Unwatch a ban command ");
			}
			if (s.hasPermission("oppermissions.opbancommands.check") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opbancommands check <command> " + ChatColor.AQUA + "- Check to see if a ban command is watched ");
			}
			if (s.hasPermission("oppermissions.opbancommands.list") || showAll) {
				s.sendMessage(ChatColor.DARK_PURPLE + "/opbancommands list " + ChatColor.AQUA + "- List the watched ban commands ");
			}
		}
	}
	
	private void helpConfigFields(CommandSender s) {
		helpConfigFields(s, 1); 
	}
	
	private void helpConfigFields(CommandSender s, int page) {
		if (page == 1) {
			s.sendMessage(ChatColor.DARK_AQUA + "The commands for setting the config fields of the " + ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " plugin are: "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set opscanop default|op|permission|no|false " + ChatColor.AQUA + "- Set the opscanop field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set opscandeop default|op|permission|no|false " + ChatColor.AQUA + "- Set the opscandeop field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set allowrequests op|permission|both|all|no|false " + ChatColor.AQUA + "- Set the allowrequests field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set useuuids true|false " + ChatColor.AQUA + "- Set the useuuids field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set updateonplayerjoins true|false " + ChatColor.AQUA + "- Set the updateonplayerjoins field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set onlyautoupdateonline true|false " + ChatColor.AQUA + "- Set the onlyautoupdateonline field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set announceops all|permanent|normal|no|false " + ChatColor.AQUA + "- Set the announceops field "); 
		}
		else {
			s.sendMessage(ChatColor.DARK_AQUA + "The commands for setting the config fields of the " + ChatColor.AQUA + plugin.getName() + ChatColor.DARK_AQUA + " plugin are: "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set permanentopsusecommands true|false " + ChatColor.AQUA + "- Set the permanentopsusecommands field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set showopattempts true|false " + ChatColor.AQUA + "- Set the showopattempts field "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set showcommanduse true|false " + ChatColor.AQUA + "- Set the showcommanduse field "); 
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set bannableops op|permanent|permission|default|no|false " + ChatColor.AQUA + "- Set the bannableops field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set bannablepermanentops op|permanent|permission|default|no|false " + ChatColor.AQUA + "- Set the bannablepermanentops field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set deoponban true|false " + ChatColor.AQUA + "- Set the deoponban field ");
			s.sendMessage(ChatColor.DARK_PURPLE + "/opset config set showbanattempts true|false " + ChatColor.AQUA + "- Set the showbanattempts field ");
		}
	}
	
	private void configUpdateMessage() {
		Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + " The config has been updated ", "oppermissions.seepluginmessages"); 
	}
	
	@SuppressWarnings("deprecation")
	private void updatePlayerUUIDStatus(boolean UUIDStatus) {
		if (UUIDStatus == true) {
			plugin.getConfig().set("useuuids", true); 
			List<String> opNames = plugin.getConfig().getStringList("ops"); 
			List<String> opUUIDs = new ArrayList<>(); 
			for (String i : opNames) {
				if (opUUIDs.contains(Bukkit.getOfflinePlayer(i).getUniqueId().toString()) == false) {
					if (!(plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(i).isOnline() == false)) {
						opUUIDs.add(Bukkit.getOfflinePlayer(i).getUniqueId().toString()); 
					}
					else {
						opUUIDs.add(i); 
					}
				}
			}
			plugin.getConfig().set("ops", opUUIDs); 
			configUpdateMessage(); 
		}
		else {
			plugin.getConfig().set("useuuids", false); 
			List<String> opUUIDs = plugin.getConfig().getStringList("ops"); 
			List<String> opNames = new ArrayList<>(); 
			for (String i : opUUIDs) {
				try {
					if (opNames.contains(Bukkit.getOfflinePlayer(UUID.fromString(i)).getName()) == false) {
						if (!(plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(UUID.fromString(i)).isOnline() == false)) {
							opNames.add(Bukkit.getOfflinePlayer(UUID.fromString(i)).getName()); 
						}
						else {
							opNames.add(i); 
						}
					}
				}
				catch (IllegalArgumentException e) {
					if (opNames.contains(i) == false) {
						opNames.add(i); 
					}
				}
			}
			plugin.getConfig().set("ops", opNames); 
			configUpdateMessage(); 
		}
	}
	
	void updateRegex(String key, List<String> expressions) {
		if (expressions.isEmpty() || (expressions.size() == 0)) {
			plugin.regex.put(key, Pattern.compile("^$")); 
		}
		else {
			String expression = expressions.get(0); 
			if (expressions.size() > 1) {
				for (String i : expressions) {
					expression += "|"; 
					expression += i; 
				}
			}
			plugin.regex.put(key, Pattern.compile(expression)); 
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (l.equalsIgnoreCase("opset")) {
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("set")) {
					if (args[2].equalsIgnoreCase("opscanop") || args[2].equalsIgnoreCase("opscandeop")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("op") || args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("no") || args[3].equalsIgnoreCase("default") || args[3].equalsIgnoreCase("false")) {
								plugin.getConfig().set(args[2].toLowerCase(), args[3].toLowerCase()); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the 'opscanop' and 'opscandeop' fields must be either 'op', 'permission', 'default', 'false' or 'no'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("allowrequests")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("op") || args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("no") || args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("both")) {
								plugin.getConfig().set("allowrequests", args[3].toLowerCase()); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the 'allowrequests' field must be either 'op', 'permission', 'false', 'all', 'both' or 'no'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("useuuids")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (plugin.getConfig().getString("useuuids").equalsIgnoreCase(args[3])) {
								s.sendMessage(ChatColor.RED + "This config value is already " + args[3]); 
							}
							else if (args[3].equalsIgnoreCase("true")) {
								updatePlayerUUIDStatus(true); 
								configUpdateMessage(); 
							}
							else if (args[3].equalsIgnoreCase("false")) {
								updatePlayerUUIDStatus(false); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the 'useuuids' field must be either 'true' or 'false'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("updateonplayerjoins") || args[2].equalsIgnoreCase("onlyautoupdateonline") || args[2].equalsIgnoreCase("permanentopsusecommands") || args[2].equalsIgnoreCase("showopattempts") || args[2].equalsIgnoreCase("showcommanduse") || args[2].equalsIgnoreCase("deoponban") || args[2].equalsIgnoreCase("showbanattempts")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("true")) {
								plugin.getConfig().set(args[2], true); 
								configUpdateMessage(); 
							}
							else if (args[3].equalsIgnoreCase("false")) {
								plugin.getConfig().set(args[2], false); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the " + args[2] + " field must be either 'true' or 'false'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("announceops")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("all") || args[3].equalsIgnoreCase("permanent") || args[3].equalsIgnoreCase("normal") || args[3].equalsIgnoreCase("no") || args[3].equalsIgnoreCase("false")) {
								plugin.getConfig().set("announceops", args[3].toLowerCase()); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the " + args[2] + " field must be 'all', 'permanent', 'normal', 'no' or 'false'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[2].equalsIgnoreCase("bannableops") || args[2].equalsIgnoreCase("bannablepermanentops")) {
						if (s.hasPermission("oppermissions.config.set." + args[2].toLowerCase()) || (s instanceof ConsoleCommandSender)) {
							if (args[3].equalsIgnoreCase("default") || args[3].equalsIgnoreCase("permanent") || args[3].equalsIgnoreCase("op") || args[3].equalsIgnoreCase("permission") || args[3].equalsIgnoreCase("no") || args[3].equalsIgnoreCase("false")) {
								plugin.getConfig().set(args[2].toLowerCase(), args[3].toLowerCase()); 
								configUpdateMessage(); 
							}
							else {
								s.sendMessage(ChatColor.RED + "The value of the " + args[2] + " field must be 'default', 'permanent', 'op', 'permission', 'no' or 'false'"); 
							}
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else {
						return false; 
					}
					plugin.saveConfig(); 
				}
				else {
					return false; 
				}
			}
			else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("all")) {
					if (s.hasPermission("oppermissions.showallhelp") || (s instanceof ConsoleCommandSender)) {
						if (args[2].equalsIgnoreCase("1")) {
							helpCommand(s, true, 1); 
						}
						else if (args[2].equalsIgnoreCase("2")) {
							helpCommand(s, true, 2); 
						}
						else if (args[2].equalsIgnoreCase("3")) {
							helpCommand(s, true, 3); 
						}
						else {
							s.sendMessage(ChatColor.RED + "There are only three pages of help commands (1, 2 and 3) "); 
							return false; 
						}
					}
				}
				else if (args[0].equalsIgnoreCase("help") && args[1].equalsIgnoreCase("config")) {
					if ((s instanceof ConsoleCommandSender) || (s.hasPermission("oppermissions.config.seedetailedsethelp")) || (s.hasPermission("oppermissions.showallhelp"))) {
						if (args[2].equalsIgnoreCase("1")) {
							helpConfigFields(s); 
						}
						else if (args[2].equalsIgnoreCase("2")) {
							helpConfigFields(s, 2); 
						}
						else {
							s.sendMessage(ChatColor.RED + "There are only two pages of config help commands (1 and 2) "); 
							return false; 
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
						OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]); 
						if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && p.isOnline() == false) {
							s.sendMessage(ChatColor.RED + "The player is not online "); 
						}
						else {
							List<String> ops = plugin.getConfig().getStringList("ops"); 
							String playerId = null; 
							if (plugin.getConfig().getBoolean("useuuids") == true) {
								playerId = p.getUniqueId().toString(); 
							}
							else {
								playerId = p.getName(); 
							}
							Boolean containsId = false; 
							for (String i : ops) {
								if (i.equalsIgnoreCase(playerId)) {
									containsId = true; 
									if (plugin.getConfig().getBoolean("useuuids") == false) {
										playerId = i; 
									}
								}
							}
							if (containsId == true) {
								 s.sendMessage(ChatColor.RED + args[1] + " was already on the list "); 
							}
							else {
								ops.add(playerId); 
								plugin.getConfig().set("ops", ops); 
								p.setOp(true); 
								s.sendMessage(ChatColor.GREEN + args[1] + " added to the permanent ops list "); 
								Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + args[1] + " was added to the permanent ops list", "oppermissions.seepluginmessages"); 
							}
							plugin.saveConfig(); 
						}
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else if (args[0].equalsIgnoreCase("remove")) {
					if (s.hasPermission("oppermissions.remove") || (s instanceof ConsoleCommandSender)) {
						String playerId = args[1]; 
						if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(playerId).isOnline() == false) {
							s.sendMessage(ChatColor.RED + "The player is not online "); 
						}
						else {
							if (plugin.getConfig().getBoolean("useuuids") == true) {
								playerId = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString(); 
							}
							List<String> ops = plugin.getConfig().getStringList("ops"); 
							Boolean containsId = false; 
							for (String i : ops) {
								if (i.equalsIgnoreCase(playerId)) {
									containsId = true; 
									if (plugin.getConfig().getBoolean("useuuids") == false) {
										playerId = i; 
									}
								}
							}
							if (containsId == false) {
								s.sendMessage(ChatColor.RED + args[1] + " could not be found in the permanent ops list "); 
							}
							else {
								ops.remove(playerId); 
								plugin.getConfig().set("ops", ops); 
								s.sendMessage(ChatColor.GREEN + args[1] + " removed from the permanent ops list "); 
								Bukkit.broadcast(ChatColor.GREEN + plugin.formattedPluginName + args[1] + " was removed from the permanent ops list", "oppermissions.seepluginmessages"); 
							}
							plugin.saveConfig(); 
						}
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else if (args[0].equalsIgnoreCase("check")) {
					if (s.hasPermission("oppermissions.check") || (s instanceof ConsoleCommandSender)) {
						String playerId = args[1]; 
						if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(playerId).isOnline() == false) {
							s.sendMessage(ChatColor.RED + "The player is not online "); 
						}
						else {
							if (plugin.getConfig().getBoolean("useuuids") == true) {
								playerId = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString(); 
							}
							if (plugin.getConfig().getStringList("ops").contains(playerId)) {
								s.sendMessage(args[1] + " is a permanent op "); 
							}
							else {
								s.sendMessage(args[1] + " is not a permanent op "); 
							}
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
							Bukkit.broadcast(ChatColor.GREEN + plugin.getName() + " configuration saved ", "oppermissions.seepluginmessages"); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[1].equalsIgnoreCase("reload")) {
						if (s.hasPermission("oppermissions.config.reload") || (s instanceof ConsoleCommandSender)) {
							plugin.reloadConfig(); 
							updateRegex("commands", plugin.getConfig().getStringList("commands")); 
							updateRegex("opbancommands", plugin.getConfig().getStringList("opbancommands")); 
							Bukkit.broadcast(ChatColor.GREEN + plugin.getName() + " configuration reloaded ", "oppermissions.seepluginmessages"); 
							s.sendMessage(ChatColor.DARK_RED + "You may want to issue the command */opset config verifylist* for all config updates to take effect "); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[1].equalsIgnoreCase("verifylist")) {
						if (s.hasPermission("oppermissions.config.verifylist") || (s instanceof ConsoleCommandSender)) {
							updatePlayerUUIDStatus(plugin.getConfig().getBoolean("useuuids")); 
							plugin.saveConfig(); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else {
						return false; 
					}
				}
				else if (args[0].equalsIgnoreCase("help")) {
					 if (args[1].equalsIgnoreCase("all")) {
						if (s.hasPermission("oppermissions.showallhelp") || (s instanceof ConsoleCommandSender)) {
							helpCommand(s, true, 1); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[1].equalsIgnoreCase("config")) {
						if ((s instanceof ConsoleCommandSender) || (s.hasPermission("oppermissions.config.seedetailedsethelp")) || (s.hasPermission("oppermissions.showallhelp"))) {
							helpConfigFields(s, 1); 
						}
						else {
							plugin.noPermission(s); 
						}
					}
					else if (args[1].equalsIgnoreCase("3")) {
						helpCommand(s, false, 3); 
					}
					else if (args[1].equalsIgnoreCase("2")) {
						helpCommand(s, false, 2); 
					}
					else if (args[1].equalsIgnoreCase("1")) {
						helpCommand(s); 
					}
					else {
						s.sendMessage(ChatColor.RED + args[1] + " is not a valid help page "); 
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
						List<String> opUUIDs = plugin.getConfig().getStringList("ops"); 
						List<String> opNames = new ArrayList<>(); 
						for (String i : opUUIDs) {
							try {
								opNames.add(Bukkit.getOfflinePlayer(UUID.fromString(i)).getName()); 
							}
							catch (IllegalArgumentException e) {
								opNames.add(i); 
							}
						}
						if (opNames.isEmpty()) {
							s.sendMessage(ChatColor.DARK_RED + "There are no permanent ops "); 
						}
						else {
							s.sendMessage(ChatColor.DARK_GREEN + opNames.toString()); 
						}
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
				String canSeeMessage = plugin.getConfig().getString("allowrequests"); 
				if (s.hasPermission("oppermissions.oprequest.send") || (s instanceof ConsoleCommandSender)) {
					if (plugin.getConfig().getString("allowrequests").equalsIgnoreCase("no") || plugin.getConfig().getString("allowrequests").equalsIgnoreCase("false")) {
						s.sendMessage(ChatColor.RED + "This feature has been disabled "); 
					}
					else if (!(canSeeMessage.equalsIgnoreCase("all") || canSeeMessage.equalsIgnoreCase("both") || canSeeMessage.equalsIgnoreCase("op") || canSeeMessage.equalsIgnoreCase("permission"))) {
						s.sendMessage(ChatColor.RED + "This feature has been disabled "); 
						Bukkit.broadcast(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value ", "oppermissions.seepluginmessages"); 
						Bukkit.broadcast(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value ", "oppermissions.oprequest.receive"); 
						plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the allowrequests field (it should be 'op', 'permission', 'all', 'both', 'false' or 'no'"); 
					}
					else {
						String message = String.join(" ", args); 
						message = ChatColor.DARK_GREEN + s.getName() + " asks: " + ChatColor.GREEN + message; 
						if (canSeeMessage.equalsIgnoreCase("all")) {
							Bukkit.broadcastMessage(message); 
						}
						else {
							if (canSeeMessage.equalsIgnoreCase("permission")) {
								Bukkit.broadcast(message, "oppermissions.oprequest.receive"); 
								if ((s instanceof Player) && (s.hasPermission("oppermissions.oprequest.receive") == false)) {
									s.sendMessage(message); 
								}
							}
							else if (canSeeMessage.equalsIgnoreCase("op")) {
								Bukkit.broadcast(message, Server.BROADCAST_CHANNEL_ADMINISTRATIVE); 
								if ((s instanceof Player) && (s.isOp() == false)) {
									s.sendMessage(message); 
								}
							}
							else if (canSeeMessage.equalsIgnoreCase("both")) {
								Bukkit.broadcast(message, "oppermissions.oprequest.receive"); 
								Bukkit.broadcast(message, Server.BROADCAST_CHANNEL_ADMINISTRATIVE); 
								if ((s instanceof Player) && (s.hasPermission("oppermissions.oprequest.receive") == false) && (s.isOp() == false)) {
									s.sendMessage(message); 
								}
							}
						}
					}
				}
				else {
					plugin.noPermission(s); 
				}
			}
		}
		else if (l.equalsIgnoreCase("opcommand") || l.equalsIgnoreCase("opbancommands")) {
			String section = l.equalsIgnoreCase("opbancommands") ? "opbancommands" : "commands"; 
			if (args.length == 0) {
				return false; 
			}
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					if (s.hasPermission("oppermissions." + section + ".list") || (s instanceof ConsoleCommandSender)) {
						List<String> blockedCommands = plugin.getConfig().getStringList(section); 
						if (blockedCommands.isEmpty()) {
							s.sendMessage(ChatColor.RED + "There are no blocked commands");
						}
						else {
							s.sendMessage(blockedCommands.toString());
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
			else if (l.equalsIgnoreCase("opcommand") || args.length == 2){
				if (args[0].equalsIgnoreCase("add")) {
					if (s.hasPermission("oppermissions." + section + ".add") || (s instanceof ConsoleCommandSender)) {
						List<String> blockedCommands = plugin.getConfig().getStringList(section); 
						String command = String.join(" ", args).split(" ", 2)[1]; 
						if (blockedCommands.contains(command)) {
							s.sendMessage(ChatColor.RED + "'" + ChatColor.GRAY + command + ChatColor.RED + "' is already a blocked command "); 
						}
						else {
							blockedCommands.add(command); 
							plugin.getConfig().set(section, blockedCommands); 
							plugin.saveConfig();
							updateRegex(section, blockedCommands); 
							s.sendMessage(ChatColor.GREEN + "'" + ChatColor.GRAY + command + ChatColor.GREEN + "' is now blocked "); 
						}
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else if (args[0].equalsIgnoreCase("remove")) {
					if (s.hasPermission("oppermissions." + section + ".remove") || (s instanceof ConsoleCommandSender)) {
						List<String> blockedCommands = plugin.getConfig().getStringList(section); 
						String command = String.join(" ", args).split(" ", 2)[1]; 
						if (blockedCommands.contains(command)) {
							blockedCommands.remove(command); 
							plugin.getConfig().set(section, blockedCommands); 
							plugin.saveConfig();
							updateRegex(section, blockedCommands); 
							s.sendMessage(ChatColor.GREEN + "'" + ChatColor.GRAY + command + ChatColor.GREEN + "' is no longer blocked "); 
						}
						else {
							s.sendMessage(ChatColor.RED + "'" + ChatColor.GRAY + command + ChatColor.RED + "' is not a blocked command so could not be unblocked "); 
						}
					}
					else {
						plugin.noPermission(s); 
					}
				}
				else if (args[0].equalsIgnoreCase("check")) {
					if (s.hasPermission("oppermissions." + section + ".check") || (s instanceof ConsoleCommandSender)) {
						List<String> blockedCommands = plugin.getConfig().getStringList(section); 
						String command = String.join(" ", args).split(" ", 2)[1]; 
						if (blockedCommands.contains(command)) {
							s.sendMessage(ChatColor.GREEN + "'" + ChatColor.GRAY + command + ChatColor.GREEN + "' is a blocked command "); 
						}
						else {
							s.sendMessage(ChatColor.GREEN + "'" + ChatColor.GRAY + command + ChatColor.GREEN + "' is not a blocked command ");
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
			else {
				return false; 
			}
		}
		else {
			return false; 
		}
		return true;
	}
}
