# OpPermissions
## Description: 
This is a simple Minecraft Spigot plugin for managing which ops can use the op and deop commands to op players and deop other ops. 

This plugin maintains a list of 'permanent ops' and prevents people on this list from being deopped until someone with the correct permissions removes them from the list. It can also be configured so that players need to have a permission before they can use the 'op' command or the 'deop' command. 

The terms 'username' and 'playername' are used interchangeably in this plugin and its descriptions. 

This plugin can use either playernames or player UUIDs. Although this plugin can convert between the two, errors are likely to occur (most commonly users' usernames becoming 'null') if the conversion operation is performed on an offline player. As such, the format to use should be chosen at the start and kept constant. If a conversion is necessary, setting the 'onlyautoupdateonline' config field to true will prevent the plugin updating offline players, avoiding errors. Setting the 'updateonplayerjoins' config field to true will make the plugin convert the players when they come online, however, the user may still appear as 'null' until then. 

## Versions:
The current plugin release version is 1.0.1.1. The compiled .jar file is available in the 'releases' section. The 'Jar' folder contains the most recently compiled plugin version, which will be either a release or a pre-release. The pre-releases may be unstable. The current release of this plugin has been tested on Spigot servers running Minecraft versions 1.7.10, 1.8.9 and 1.12.2. It is designed for Minecraft versions between 1.7.x and 1.12.x, and as such, any bug for any version between 1.7.x and 1.12.x will be fixed. This plugin is likely to work with many other Minecraft Spigot and Bukkit versions but this is untested and bugs found with these versions will not necessarily be fixed. 

## License: 
This plugin and its source code are released under the MIT license (see the [LICENSE file](https://github.com/aappleton8/OpPermissions/blob/master/LICENSE) for full details). This plugin is copyright (c) aappleton3/aappleton8, 2018.  

## Build: 
This plugin is built using Eclipse Java Oxygen. To build the plugin, it should be opened in the aforementioned program. The .classpath file should then be recreated, and the craftbukkit-1.12.2.jar file needs to be added as an external build dependency. 

## Commands: 
The general command syntaxes are: 
 - */opset &lt;add|remove|list|version|help [1|2|all [1|2]|config]|check|config &lt;action&gt; [field]&gt; [playername|data]*. 
 - */oplist [online|offline|both]*. 
 - */oppermissions*. 
 - */oprequest &lt;message&gt;*. 
 
Below, the individual commands are listed: 
 - /oppermissions - Show the help screen 
 - /opset help [1|2] - Show the help screen 
 - /opset help all [1|2] - Show help for all commands, even ones the player does not have permission to use 
 - /opset help config - Show help for setting individual config file fields 
 - /opset version - Show the plugin version 
 - /opset add &lt;playername&gt; - Adds a player to the list 
 - /opset remove &lt;playername&gt; - Removes a player from the list 
 - /opset config reload - Reload the config file 
 - /opset config save - Save the config file 
 - /opset config set opscanop &lt;default|op|permission|no|false&gt; - Set if ops can use the */op* command 
 - /opset config set opscandeop &lt;default|op|permission|no|false&gt; - Set if ops can use the */deop* command 
 - /opset config set allowrequests &lt;op|permission|no&gt; - Enables or disables the */oprequest* command and sets which players see the request
 - /opset config set useuuids &lt;true|false&gt; - Set whether the plugin should use usernames or UUIDs 
 - /opset config set updateonplayerjoins &lt;true|false&gt; - Specify if the plugin should check user information each time the user joins 
 - /opset config set onlyautoupdateonline &lt;true|false&gt; - Specify whether the plugin should only automatcally update information for online players or all players (choosing 'false' for all players may perform operations on incorrect or non-existent players) 
 - /opset config set announceops &lt;all|permanent|normal|no|false&gt; - Specify of the plugin should announce when ops join 
 - /opset config verifylist - Update the ops list to convert between UUIDs and playernames, depending on the 'useuuids' config field and the 'onlyautoupdateonline' config field (this conversion will also happen when the 'useuuids' field is set) 
 - /opset list - List the list 
 - /opset check &lt;playername&gt; - Check if someone is on the list 
 - /oplist - List all the ops on the server, depending on the given permissions 
 - /oplist both - List both the online and offline ops 
 - /oplist online - List the online ops 
 - /oplist offline - List the offline ops 
 - /oprequest &lt;message&gt; - Send a message to the ops 
  
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
 - oppermissions.config.set.opscanop : The */opset config set opscanop &lt;value&gt;* command | Default: false 
 - oppermissions.config.set.allowrequests : The */opset config set allowrequests &lt;value&gt;* command | Default: false 
 - oppermissions.config.set.useuuids : The */opset config set useuuids &lt;value&gt;* command | Default: false 
 - oppermissions.config.set.updateonplayerjoins : The */opset config set updateonplayerjoins &lt;value&gt;* command | Default: false 
 - oppermissions.config.set.onlyautoupdateonline : The */opset config set onlyautoupdateonline &lt;value&gt;* command | Default: false
 - oppermissions.config.set.announceops : The */opset config set announceops &lt;all|permanent|normal|no|false&gt;* command | Default: false
 - oppermissions.config.seedetailedsethelp : The */opset help config* command | Default: false 
 - oppermissions.config.verifylist : The */opset config updateplayeruuids* command | Default: false 
 - oppermissions.op : Enable the use of the */op* command, depending on the config file | Default: false 
 - oppermissions.deop : Enable the use of the */deop* command, depending on the config file | Default: false 
 - oppermissions.oplist.* : Enable the use of all the */oplist* commands | Default: op 
 - oppermissions.oplist.online : Enable the use of the the */oplist online* command | Default: op 
 - oppermissions.oplist.offline : Enable the use of the */oplist offline* command | Default: op 
 - oppermissions.showallhelp : Enable the use of the */opset help all* command | Default: op 
 - oppermissions.oprequest.* : Enable the player to send a receive op requests, depending on the config file | Default: op 
 - oppermissions.oprequest.send : Enable the use of the */oprequest &lt;message&gt;* command, depending on the config file | Default: op 
 - oppermissions.oprequest.receive : Enable the player to see op requests, depending on the config file | Default: op 
 - oppermissions.seepluginmessages : Enable the player to see plugin messages (such as config save messages) | Default: op 

## Config: 
All configurable options for this plugin are in the 'config.yml' file. This file contains the following fields.  
 - useuuids - Sets whether the plugin should use usernames or UUIDs (true: use UUIDs; false: use usernames) 
 - updateonplayerjoins - Sets whether the plugin should check the information of a player each time it joins or not (true: check player information; false: don't check player information) 
 - onlyautoupdateonline - Sets whether the plugin should only allow opping, deopping and updating information of online players or online and offline players (there is no guarentee that the information changed for offline players will be correct) (true: online players only; false: online and offline players) 
 - allowrequests - Sets whether players can use the */oprequest* command or not (op: the requests are sent to ops; permission: the requests are sent to players with the permission *oppermissions.oprequest.see*; both: the requests are sent to ops and people with the permission *oppermissions.oprequest.see*; all: the requests are sent to everyone; no|false: requests cannot be made)  
 - opscanop - Sets whether ops can use the */op* command or not (default: the default happens; op: the player must be an op; permission: the player must have the *oppermissions.op* permission; no|false: the command can only be issued from the console)
 - opscandeop - Sets whether ops can use the */deop* command or not (default: the default happens; op: the player must be an op; permission: the player must have the *oppermissions.op* permission; no|false: the command can only be issued from the console)
 - announceops - Sets if the plugin should send a message to all plauetrs each time an op joins the server (all: the message is sent if any op joins; permanent: the message is sent if an op on the permanent ops list joins; normal: the message is sent if an op not on the permanent ops list joins; no|false: no message is sent) 
 - ops - The list of permanent ops 

The default config file (config.yml) is given below: 
```YAML
useuuids: false
updateonplayerjoins: true
onlyautoupdateonline: false
allowrequests: no
opscanop: default
opscandeop: default
announceops: no
ops: 
  - aappleton3 
  - Codefined
```



