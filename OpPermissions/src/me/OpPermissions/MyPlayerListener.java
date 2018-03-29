package me.OpPermissions;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR) 
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getBoolean("updateonplayerjoins") == true) {
			List<String> ops = plugin.getConfig().getStringList("ops"); 
			Player player = event.getPlayer(); 
			if (event.getPlayer().hasPlayedBefore() == false) {
				if (ops.contains(player.getName()) || ops.contains(player.getUniqueId().toString())) {
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
					if (ops.contains(player.getUniqueId().toString())) {
						ops.add(player.getUniqueId().toString()); 
					}
					plugin.getConfig().set("ops", ops); 
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (ignoreCancelled = false, priority = EventPriority.HIGHEST) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().startsWith("/deop")) {
			String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
			if (rawArgs.length == 2 && rawArgs[0].equalsIgnoreCase("deop")) { 
				List<String> permenantOps = plugin.getConfig().getStringList("ops"); 
				String playerId = rawArgs[1]; 
				if (plugin.getConfig().getBoolean("useuuids") == true) {
					playerId = Bukkit.getOfflinePlayer(playerId).getUniqueId().toString(); 
				}
				for (String i : permenantOps) {
					if ((i.equalsIgnoreCase(playerId)) && (!(event.getPlayer().hasPermission("oppermissions.remove")))) {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to deop this player "); 
					}
					else if ((i.equalsIgnoreCase(playerId)) && (event.getPlayer().hasPermission("oppermissions.remove")) && (event.isCancelled() == false)) {
						List<String> ops = plugin.getConfig().getStringList("ops"); 
						ops.remove(playerId); 
						plugin.getConfig().set("ops", ops); 
						plugin.saveConfig(); 
					}
				}
			}
		}
		else if (event.getMessage().startsWith("/op")) {
			String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
			if (rawArgs.length == 2 && rawArgs[0].equalsIgnoreCase("op")) {
				String opSetting = plugin.getConfig().getString("opscanop"); 
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
						event.getPlayer().sendMessage(ChatColor.RED + "You must be an op to op this player "); 
					}
				}
				else if (opSetting.equalsIgnoreCase("permission")) {
					// Only allow the action if the player has the permission 
					if (event.getPlayer().hasPermission("oppermissions.op")) {
						// No further action is required 
					}
					else {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to op this player "); 
					}
				}
				else if (opSetting.equalsIgnoreCase("no")) {
					// The command is only allowed from the console 
					event.setCancelled(true); 
					event.getPlayer().sendMessage(ChatColor.RED + "This command is only allowed from the console "); 
				}
				else {
					event.getPlayer().sendMessage(ChatColor.RED + "The " + plugin.getName() + " plugin config has an invalid value "); 
					plugin.logger.warning(plugin.formattedPluginName + "The config has an invalid value in the opscanop field (it should be 'op', 'permission', 'default' or 'no'"); 
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
				List<String> permenantOps = plugin.getConfig().getStringList("ops"); 
				String playerId = rawArgs[1]; 
				if (plugin.getConfig().getBoolean("useuuids") == true) {
					playerId = Bukkit.getOfflinePlayer(playerId).getUniqueId().toString(); 
				}
				for (String i : permenantOps) {
					if (i.equalsIgnoreCase(playerId)) {
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
