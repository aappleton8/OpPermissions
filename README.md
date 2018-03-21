# OpPermissions
A simple Minecraft Spigot 1.8/1.12 plugin for managing which ops can use the deop command to deop other ops. 

This plugin maintains a list of 'permanent ops' and prevents people on this list from being deopped until someone with the correct permissions removes them from the list. It can also be configured so that players need to have a permission before they can op another player. 

Commands: 
The general command synta is: */opset <add|remove|list [player]|config> <player username|data>*. 
 Below, the individual commands are listed: 
 - /opset add <playername> - Adds a player to the list 
 - /opset remove <playername> - Removes a player from the list 
 - /opset config reload - Reload the config file 
 - /opset config save - Save the config file 
 - /opset config set <op|permission|no> - Set whether ops can use the */op* command (op - yes; permissions - with the oppermissions.op permission; no - no) 
 - /opset list - List the list 
 - /opset check <playername> - Check if someone is on the list 
  
Permission: 
- oppermissions.* : All below permissions | Default: false 
- oppermissions.read.* : The */opset list* and */opset check* commands | Default: op 
- oppermissions.read.list : The */opset list* command | Default: op 
- oppermissions.read.check : The */opset check* command | Default: op 
- oppermissions.add : The */opset add* command | Default: false 
- oppermissions.remove : The */opset remove* command | Default: false 
- oppermissions.reload : The */opset config reload* command | Default: false 
- oppermissions.save : The */opset config save* command | Default: false 
- oppermissions.setcanop : The */opset config set* command | Default: false 
- oppermissions.op : Enable the use of the */op* command, depending on the config file | Default: op 

Config: 
 - opscanop - Whether ops can use the */op* command or not 
 - ops - The list of permanent ops 

