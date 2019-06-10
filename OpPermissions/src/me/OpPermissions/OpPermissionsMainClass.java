package me.OpPermissions;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OpPermissionsMainClass extends JavaPlugin {
	public static OpPermissionsMainClass plugin; 
	
	public final Logger logger = Logger.getLogger("Minecraft"); 
	public final MyPlayerListener pl = new MyPlayerListener(this, logger); 
	private PluginDescriptionFile descriptionFile; 
	protected String formattedPluginName; 
	
	public HashMap<String, Pattern> regex = new HashMap<String, Pattern>(); 
	
	@Override
	public void onDisable() {
		saveConfig(); 
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been disabled "); 
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager(); 
		pm.registerEvents(pl, this); 
		descriptionFile = getDescription(); 
		formattedPluginName = "[" + descriptionFile.getName() + "] "; 
		saveDefaultConfig(); 
		OpPermissionsCommands opc = new OpPermissionsCommands(this); 
		getCommand("opset").setExecutor(opc); 
		getCommand("oppermissions").setExecutor(opc); 
		getCommand("oplist").setExecutor(opc); 
		getCommand("oprequest").setExecutor(opc); 
		getCommand("opcommand").setExecutor(opc); 
		getCommand("opbancommands").setExecutor(opc); 
		opc.updateRegex("commands", getConfig().getStringList("commands"));
		opc.updateRegex("opbancommands", getConfig().getStringList("opbancommands")); 
		logger.info(formattedPluginName + descriptionFile.getName() + " " + descriptionFile.getVersion() + " has been enabled "); 
	}
	
	public void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You do not have permission to use this command "); 
	}
}
