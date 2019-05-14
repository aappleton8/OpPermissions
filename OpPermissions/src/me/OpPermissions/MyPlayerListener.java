package me.OpPermissions;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class MyPlayerListener implements Listener {
	public static OpPermissionsMainClass plugin; 
	public static Logger logger; 
	public MyPlayerListener(OpPermissionsMainClass instance, Logger log) {
		plugin = instance; 
		logger = log; 
	}
	
	private void announceOp(String name) {
		Bukkit.broadcastMessage(ChatColor.DARK_AQUA + plugin.formattedPluginName + "An op (" + name + ChatColor.DARK_AQUA + ") has joined. "); 
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getBoolean("updateonplayerjoins") == true) {
			List<String> ops = plugin.getConfig().getStringList("ops"); 
			Player player = event.getPlayer(); 
			if (ops.contains(player.getName()) || ops.contains(player.getUniqueId().toString())) {
				if (player.isOp() == false) {
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "op " + player.getName()); 
				}
			}
			if (plugin.getConfig().getBoolean("useuuids") == false) {
				if (ops.contains(player.getUniqueId().toString())) {
					ops.remove(player.getUniqueId().toString()); 
					if (ops.contains(player.getName()) == false) {
						ops.add(player.getName()); 
					}
					plugin.getConfig().set("ops", ops); 
				}
			}
			else {
				if (ops.contains(player.getName())) {
					ops.remove(player.getName()); 
					if (ops.contains(player.getUniqueId().toString()) == false) {
						ops.add(player.getUniqueId().toString()); 
					}
					plugin.getConfig().set("ops", ops); 
				}
			}
		}
		if (event.getPlayer().isOp() == true) {
			String announceOps = plugin.getConfig().getString("announceops"); 
			if (!(announceOps.equalsIgnoreCase("false") || announceOps.equalsIgnoreCase("no"))) {
				List<String> ops = plugin.getConfig().getStringList("ops"); 
				Player player = event.getPlayer(); 
				if (ops.contains(player.getName()) || ops.contains(player.getUniqueId().toString())) {
					if (announceOps.equalsIgnoreCase("all") || announceOps.equalsIgnoreCase("permanent")) {
						announceOp(player.getDisplayName()); 
					}
				}
				else {
					if (announceOps.equalsIgnoreCase("all") || announceOps.equalsIgnoreCase("normal")) {
						announceOp(player.getDisplayName()); 
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void onAllPlayerCommands(PlayerCommandPreprocessEvent event) {
		if (plugin.getConfig().getBoolean("showopattempts")) {
			if (event.getMessage().startsWith("/op") || event.getMessage().startsWith("/deop")) {
				String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
				if ((rawArgs.length == 2) && (rawArgs[0].equalsIgnoreCase("op") || rawArgs[0].equalsIgnoreCase("deop"))) {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()));
					Bukkit.broadcast(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()), "oppermissions.showopattempts"); 
				}
			}
		}
		if (plugin.getConfig().getBoolean("showcommanduse")) {
			List<String> blockedCommands = plugin.getConfig().getStringList("commands"); 
			if (blockedCommands.contains(event.getMessage().replace("/", "").toLowerCase())) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()));
				Bukkit.broadcast(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()), "oppermissions.command.show"); 
			}
		}
		if (plugin.getConfig().getBoolean("showbanattempts")) {
			List<String> banCommands = plugin.getConfig().getStringList("opbancommands"); 
			String[] command = event.getMessage().replace("/", "").toLowerCase().split(" "); 
			if (command.length > 1) {
				if (banCommands.contains(command[0])) {
					if (Bukkit.getOfflinePlayer(command[1]) != null) {
						if (Bukkit.getOfflinePlayer(command[1]).isOp()) {
							Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()));
							Bukkit.broadcast(ChatColor.BLUE + plugin.formattedPluginName + event.getPlayer().getName() + " tried to use '" + event.getMessage() + "' with a success status of: " + String.valueOf(!event.isCancelled()), "oppermissions.ban.show"); 
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		boolean canDeop = true; 
		boolean checkBanCommand = false; 
		if (event.getMessage().startsWith("/op") || event.getMessage().startsWith("/deop")) {
			String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
			if ((rawArgs.length == 2) && (rawArgs[0].equalsIgnoreCase("op") || rawArgs[0].equalsIgnoreCase("deop"))) {
				if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(rawArgs[1]).isOnline() == false) {
					event.getPlayer().sendMessage(ChatColor.RED + "This player is not online "); 
					event.setCancelled(true); 
					canDeop = false; 
				}
				else {
					String opSetting = plugin.getConfig().getString("opscan" + rawArgs[0]); 
					if (opSetting.equalsIgnoreCase("default")) {
						// No action required
					}
					else if (opSetting.equalsIgnoreCase("op")) {
						// Only allow the action if the player is an op 
						if (event.getPlayer().isOp() == true) {
							// No action required 
						}
						else {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You must be an op to "  + rawArgs[0] + " this player "); 
							canDeop = false; 
						}
					}
					else if (opSetting.equalsIgnoreCase("permission")) {
						// Only allow the action if the player has the permission 
						if (event.getPlayer().hasPermission("oppermissions." + rawArgs[0])) {
							// No further action is required 
						}
						else {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to " + rawArgs[0] + " this player "); 
							canDeop = false; 
						}
					}
					else if (opSetting.equalsIgnoreCase("no") || opSetting.equalsIgnoreCase("false")) {
						// The command is only allowed from the console 
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "This command is only allowed from the console "); 
						canDeop = false; 
					}
					else {
						event.getPlayer().sendMessage(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value in the 'opscan" + rawArgs[0] + "' field and as such this command has been disallowed "); 
						plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the opscan" + rawArgs[0] + " field (it should be 'op', 'permission', 'default', 'false' or 'no'"); 
						event.setCancelled(true); 
						canDeop = false; 
					}
				}
			}
		}
		else {
			List<String> blockedCommands = plugin.getConfig().getStringList("commands"); 
			List<String> banCommands = plugin.getConfig().getStringList("opbancommands"); 
			if (blockedCommands.contains(event.getMessage().replace("/", "").toLowerCase())) {
				if (event.getPlayer().hasPermission("oppermissions.command.command")) {
					// No action required 
				}
				else if (plugin.getConfig().getBoolean("permanentopsusecommands")) {
					String playerId = null; 
					if (plugin.getConfig().getBoolean("useuuids") == false) {
						playerId = event.getPlayer().getName(); 
					}
					else {
						playerId = event.getPlayer().getUniqueId().toString(); 
					}
					if (plugin.getConfig().getStringList("ops").contains(playerId)) {
						// No action required
					}
					else {
						event.setCancelled(true);
						plugin.noPermission((CommandSender) event.getPlayer());
					}
				}
				else {
					event.setCancelled(true);
					plugin.noPermission((CommandSender) event.getPlayer());
				}
			}
			String[] commandWord = event.getMessage().split(" "); 
			if (banCommands.contains(commandWord[0].replace("/", "").toLowerCase()) && (event.isCancelled() == false) && (commandWord.length > 1)) {
				checkBanCommand = true; 
			}
		}
		if ((event.getMessage().startsWith("/deop")) && (canDeop == true)) {
			String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
			if (rawArgs.length == 2 && rawArgs[0].equalsIgnoreCase("deop")) { 
				List<String> permanentOps = plugin.getConfig().getStringList("ops"); 
				String playerId = rawArgs[1]; 
				if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(playerId).isOnline() == false) {
					event.getPlayer().sendMessage(ChatColor.RED + "This player is not online "); 
					event.setCancelled(true); 
				}
				else {
					if (plugin.getConfig().getBoolean("useuuids") == true) {
						playerId = Bukkit.getOfflinePlayer(playerId).getUniqueId().toString(); 
					}
					if (permanentOps.contains(playerId)) {
						if (!(event.getPlayer().hasPermission("oppermissions.remove"))) {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to deop this player "); 
						}
						else if (event.getPlayer().hasPermission("oppermissions.remove")) {
							List<String> ops = plugin.getConfig().getStringList("ops"); 
							ops.remove(playerId); 
							plugin.getConfig().set("ops", ops); 
							plugin.saveConfig(); 
						}
					}
				}
			}
		}
		if (checkBanCommand == true) {
			String playerNameToBan = event.getMessage().split(" ")[1]; 
			Boolean playerToBanOnList = false; 
			Boolean playerOnList = false; 
			if (Bukkit.getOfflinePlayer(playerNameToBan).isOp()) {
				if (plugin.getConfig().getBoolean("useuuids") == false) {
					if (plugin.getConfig().getStringList("ops").contains(playerNameToBan)) {
						playerToBanOnList = true; 
					}
					if (plugin.getConfig().getStringList("ops").contains(event.getPlayer().getName())) {
						playerOnList = true; 
					}
				}
				else {
					String playerToBan = Bukkit.getPlayer(playerNameToBan).getUniqueId().toString(); 
					if (plugin.getConfig().getStringList("ops").contains(playerToBan)) {
						playerToBanOnList = true; 
					}
					if (plugin.getConfig().getStringList("ops").contains(event.getPlayer().getUniqueId().toString())) {
						playerOnList = true; 
					}
				}
				String bannableOps = plugin.getConfig().getString("bannableops"); 
				String bannablePermanentOps = plugin.getConfig().getString("bannablepermanentops"); 
				// Handle ops 
				if (bannableOps.equalsIgnoreCase("default")) {
					// No acton required 
				}
				else if (bannableOps.equalsIgnoreCase("no") || bannableOps.equalsIgnoreCase("false")) {
					event.setCancelled(true); 
					event.getPlayer().sendMessage(ChatColor.RED + "Only the console can punish ops "); 
				}
				else if (bannableOps.equalsIgnoreCase("op")) {
					if (event.getPlayer().isOp() == false) {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED  + "You must be an op to punish an op "); 
					}
				}
				else if (bannableOps.equalsIgnoreCase("permanent")) {
					if (playerOnList == false) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "You must be a permanent op to ban an op "); 
					}
				}
				else if (bannableOps.equalsIgnoreCase("permission")) {
					if (event.getPlayer().hasPermission("oppermissions.ban.ops") == false) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED + "You need the permission 'oppermissions.ban.ops' to ban an op "); 
					}
				}
				else {
					event.getPlayer().sendMessage(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value in the 'bannableops' field and as such this command has been disallowed "); 
					plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the 'bannableops' field (it should be 'op', 'permission', 'permanent', 'default', 'false' or 'no'"); 
					event.setCancelled(true); 
				}
				// Handle permanent ops 
				if ((event.isCancelled() == false) && (playerToBanOnList == true)) {
					if (bannablePermanentOps.equalsIgnoreCase("default")) {
						// No action required 
					}
					else if (bannablePermanentOps.equalsIgnoreCase("no") || bannablePermanentOps.equalsIgnoreCase("false")) {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "Only the console can punish permanent ops "); 
					}
					else if (bannablePermanentOps.equalsIgnoreCase("op")) {
						if (event.getPlayer().isOp() == false) {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You must be an op to punish a permanent op "); 
						}
					}
					else if (bannablePermanentOps.equalsIgnoreCase("permanent")) {
						if (playerOnList == false) {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You must be a permanent op to punish a permanent op "); 
						}
					}
					else if (bannablePermanentOps.equalsIgnoreCase("permission")) {
						if (event.getPlayer().hasPermission("oppermissions.ban.permanentops") == false) {
							event.setCancelled(true); 
							event.getPlayer().sendMessage(ChatColor.RED + "You need the permission 'oppermissions.ban.permanentops' to ban a permanent op "); 
						}
					}
					else {
						event.getPlayer().sendMessage(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value in the 'bannablepermanentops' field and as such this command has been disallowed "); 
						plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the 'bannablepermanentops' field (it should be 'op', 'permission', 'permanent', 'default', 'false' or 'no'"); 
						event.setCancelled(true); 
					}
				}
				if (event.isCancelled() == false) {
					if (plugin.getConfig().getBoolean("deoponban")) {
						if (plugin.getConfig().getBoolean("useuuids") == true) {
							playerNameToBan = Bukkit.getOfflinePlayer(playerNameToBan).getUniqueId().toString(); 
						}
						if (playerToBanOnList) {
							List<String> ops = plugin.getConfig().getStringList("ops"); 
							ops.remove(playerNameToBan); 
							plugin.getConfig().set("ops", ops); 
							plugin.saveConfig(); 
							Bukkit.getOfflinePlayer(playerNameToBan).setOp(false); 
							Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + plugin.formattedPluginName + playerNameToBan + " was deopped due to being banned ");
							Bukkit.broadcast(ChatColor.BLUE + plugin.formattedPluginName + playerNameToBan + " was deopped due to being banned ", "oppermissions.ban.show"); 
							Bukkit.broadcast(ChatColor.BLUE + plugin.formattedPluginName + playerNameToBan + " was deopped due to being banned ", "oppermissions.seepluginmessages"); 
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST) 
	public void onServerCommand(ServerCommandEvent event) {
		if (event.getCommand().startsWith("deop")) {
			String[] rawArgs = event.getCommand().split(" "); 
			if (rawArgs.length == 2 && rawArgs[0].equalsIgnoreCase("deop")) {
				if (plugin.getConfig().getBoolean("onlyautoupdateonline") == true && Bukkit.getOfflinePlayer(rawArgs[1]).isOnline() == false) {
					event.getSender().sendMessage(ChatColor.RED + "This player is not online "); 
					event.setCancelled(true); 
				}
				else {
					List<String> permanentOps = plugin.getConfig().getStringList("ops"); 
					String playerId = rawArgs[1]; 
					if (plugin.getConfig().getBoolean("useuuids") == true) {
						playerId = Bukkit.getOfflinePlayer(playerId).getUniqueId().toString(); 
					}
					if (permanentOps.contains(playerId)) {
						List<String> ops = plugin.getConfig().getStringList("ops"); 
						ops.remove(playerId); 
						plugin.getConfig().set("ops", ops); 
						plugin.saveConfig(); 
					}
				}
			}
		}
	}
}
