package me.OpPermissions;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class MyPlayerListener implements Listener {
	public static OpPermissionsMainClass plugin; 
	public static Logger logger; 
	public MyPlayerListener(OpPermissionsMainClass instance, Logger log) {
		plugin = instance; 
		logger = log; 
	}
	
	@EventHandler (ignoreCancelled = false, priority = EventPriority.HIGHEST) 
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		if (event.getMessage().startsWith("/deop")) {
			String[] rawArgs = event.getMessage().replace("/", "").split(" "); 
			List<String> args = Arrays.asList(rawArgs); 
			List<String> permenantOps = plugin.getConfig().getStringList("ops"); 
			if (args.size() == 2) { 
				for (String i : permenantOps) {
					if ((i.equalsIgnoreCase(args.get(1))) && (!(event.getPlayer().hasPermission("oppermissions.remove")))) {
						event.setCancelled(true); 
						event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to deop this player "); 
					}
					else if ((i.equalsIgnoreCase(args.get(1))) && (event.getPlayer().hasPermission("oppermissions.remove")) && (event.isCancelled() == false)) {
						List<String> ops = plugin.getConfig().getStringList("ops"); 
						plugin.getConfig().set("ops", null); 
						ops.remove(args.get(1)); 
						plugin.getConfig().set("ops", ops); 
						plugin.saveConfig(); 
					}
				}
			}
		}
	}
	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST) 
	public void onServerCommand(ServerCommandEvent event) {
		if (event.getCommand().startsWith("/deop")) {
			String[] rawArgs = event.getCommand().replace("/", "").split(" "); 
			List<String> args = Arrays.asList(rawArgs); 
			List<String> permenantOps = plugin.getConfig().getStringList("ops"); 
			if (args.size() == 2) {
				for (String i : permenantOps) {
					if (i.equalsIgnoreCase(args.get(1))) {
						List<String> ops = plugin.getConfig().getStringList("ops"); 
						plugin.getConfig().set("ops", null); 
						ops.remove(args.get(1)); 
						plugin.getConfig().set("ops", ops); 
						plugin.saveConfig(); 
					}
				}
			}
		}
	}
}
