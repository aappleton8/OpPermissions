# OpPermissions
## Description: 
A simple Minecraft Spigot 1.8/1.12 plugin for managing which ops can use the deop command to deop other ops. 

This plugin maintains a list of 'permanent ops' and prevents people on this list from being deopped until someone with the correct permissions removes them from the list. It can also be configured so that players need to have a permission before they can op another player. 

This plugin and its source code are released under the MIT license. 

The terms 'username' and 'playername' are used interchangeably in this plugin and its descriptions. 

## Commands: 
The general command syntaxes are: 
 - */opset &ltadd|remove|list|version|help [all]|check|config &ltaction&gt [field]&gt [playername|data]*. 
 - */oplist [online|offline|both]*. 
 - */oppermissions*. 
 - */oprequest &ltmessage&gt*. 
 
Below, the individual commands are listed: 
 - /oppermissions - Show the help screen 
 - /opset help - Show the help screen 
 - /opset help all - Show help for all commands, even ones the player does not have permission to use 
 - /opset version - Show the plugin version 
 - /opset add &ltplayername&gt - Adds a player to the list 
 - /opset remove &ltplayername&gt - Removes a player from the list 
 - /opset config reload - Reload the config file 
 - /opset config save - Save the config file 
 - /opset config set opscanop &ltdefault|op|permission|no&gt - Set whether ops can use the */op* command 
 - /opset config set allowrequests &ltop|permission|no&gt - Set whether players can use the */oprequest* command 
 - /opset config set useuuids &lttrue|false&gt - Set whether the plugin should use usernames or UUIDs 
 - /opset config set updateonplayerjoins &lttrue|false&gt - Specify if the plugin should check user information each time the user joins 
 - /opset list - List the list 
 - /opset check &ltplayername&gt - Check if someone is on the list 
 - /oplist - List all the ops on the server, depending on the given permissions 
 - /oplist both - List both the online and offline ops 
 - /oplist online - List the online ops 
 - /oplist offline - List the offline ops 
 - /oprequest &ltmessage&gt - Send a message to the ops 
  
## Permissions: 
 - oppermissions.* : All below permissions | Default: false 
 - oppermissions.read.* : The */opset list* and */opset check* commands | Default: op 
 - oppermissions.read.list : The */opset list* command | Default: op 
 - oppermissions.read.check : The */opset check* command | Default: op 
 - oppermissions.add : The */opset add* command | Default: false 
 - oppermissions.remove : The */opset remove* command | Default: false 
 - oppermissions.config.* : All */opset config* commands | Default: false 
 - oppermissions.config.reload : The */opset config reload* command | Default: false 
 - oppermissions.config.save : The */opset config save* command | Default: false 
 - oppermissions.config.set.* : All */opset config set* commands | Default: false 
 - oppermissions.config.set.opscanop : The */opset config set opscanop &ltvalue&gt* command | Default: false 
 - oppermissions.config.set.allowrequests : The */opset config set allowrequests &ltvalue&gt* command | Default: false 
 - oppermissions.config.set.useuuids : The */opset config set useuuids &ltvalue&gt* command | Defaut: false 
 - oppermissions.config.set.updateonplayerjoins : The */opset config set updateonplayerjoins &ltvalue&gt* command | Default: false 
 - oppermissions.config.set.onlyautoupdateonline : The */opset config set onlyautoupdateonline &ltvalue&gt* command | Default: false
 - oppermissions.op : Enable the use of the */op* command, depending on the config file | Default: false 
 - oppermissions.oplist.* : Enable the use of all the */oplist* commands | Default: op 
 - oppermissions.oplist.online : Enable the use of the the */oplist online* command | Default: op 
 - oppermissions.oplist.offline : Enable the use of the */oplist offline* command | Default: op 
 - oppermissions.showallhelp : Enable the use of the */opset help all* command | Default: op 
 - oppermissions.oprequest.* : Enable the player to send a receive op requests, depending on the config file | Default: op 
 - oppermissions.oprequest.send : Enable the use of the */oprequest &ltmessage&gt* command, depending on the config file | Default: op 
 - oppermissions.oprequest.see : Enable the player to see op requests, depending on the config file | Default: op 
 - oppermissions.seepluginmessages : Enable the player to see plugin messages (such as config save messages) | Default: op 

## Config: 
All configurable options for this plugin are in the file 'config.yml'. 
 - useuuids - Sets whether the plugin should use usernames or UUIDs (true: use UUIDs; false: use usernames) 
 - updateonplayerjoins - Sets whether the plugin should check the information of a player each time it joins or not (true: check player information; false: don't check player information) 
 - onlyautoupdateonline - Sets whether the plugin should only allow opping, deopping and updating information of online players or online and offline players (there is no guarentee that the information changed for offline players will be correct) (true: online players only; false: online and offline players) 
 - allowrequests - Sets whether players can use the */oprequest* command or not (op: the requests are sent to ops; permission: the requests are sent to players with the permission *oppermissions.oprequest.see*; no: requests cannot be made)  
 - opscanop - Sets whether ops can use the */op* command or not (default: the default happens; op: the player must be an op; permission: the player must have the *oppermissions.op* permission; no: the command can only be issued from the console)
 - ops - The list of permanent ops 

The default config file (config.yml) is given below: 
```YAML
useuuids: false
updateonplayerjoins: true
onlyautoupdateonline: false
allowrequests: no
opscanop: default
ops: 
  - aappleton3 
  - Codefined
```



