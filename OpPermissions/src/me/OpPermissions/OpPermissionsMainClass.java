package me.OpPermissions;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OpPermissionsMainClass extends JavaPlugin{
	public static OpPermissionsMainClass plugin; 
	
	public final Logger logger = Logger.getLogger("Minecraft"); 
	public final MyPlayerListener pl = new MyPlayerListener(this, logger); 
	private PluginDescriptionFile descriptionFile; 
	protected String formattedPluginName; 
	
	@Override
	public void onDisable() {
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been disabled "); 
		saveConfig(); 
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager(); 
		pm.registerEvents(pl, this); 
		descriptionFile = getDescription(); 
		formattedPluginName = "[" + descriptionFile.getName() + "] "; 
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been enabled "); 
		saveDefaultConfig(); 
		OpPermissionsCommands opc = new OpPermissionsCommands(this); 
		getCommand("opset").setExecutor(opc); 
		getCommand("oppermissions").setExecutor(opc); 
		getCommand("oplist").setExecutor(opc); 
		getCommand("oprequest").setExecutor(opc); 
	}
	
	public void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You do not have permission to do this command "); 
	}
}
