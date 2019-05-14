# OpPermissions
## Description: 
This is a simple Minecraft Spigot plugin for managing which ops can use the op and deop commands to op players and deop other ops. 

This plugin maintains a list of 'permanent ops' and prevents people on this list from being deopped until someone with the correct permissions removes them from the list. This plugin can also be configured so that players need to have a permission before they can use the 'op' command or the 'deop' command at all. Rudimentary support is provided for a list of 'blocked commands' which are commands for which players (ops or otherwise) need an extra permission before they can use them. However, all aliases of the blocked commands must be listed as well as all possible combination of arguments of the blocked commands, so it is recommended to use a separate command blocking plugin if this feature is required for anything other than simple commands such as */stop*. 

The terms 'username' and 'playername' are used interchangeably in this plugin and its descriptions. 

This plugin can use either playernames or player UUIDs. Although this plugin can convert between the two, errors are likely to occur (most commonly users' usernames becoming 'null') if the conversion operation is performed on an offline player. As such, the format to use should be chosen at the start and kept constant. If a conversion is necessary, setting the 'onlyautoupdateonline' config field to true will prevent the plugin updating offline players, avoiding errors. Setting the 'updateonplayerjoins' config field to true will make the plugin convert the players when they come online, however, the user may still appear as 'null' until then. It is recommended always to use player UUIDs. 

## Versions:
The current plugin release version is 1.0.3.1. The compiled .jar file is available in the 'releases' section. The 'Jar' folder contains the most recently compiled plugin version that runs; this may be the same as the most recent release or it may be a development build. The development builds may be unstable. The current release of this plugin has been tested on Spigot servers running Minecraft versions 1.7.10, 1.8.9, 1.12.2 and 1.13.1. It is designed for Minecraft versions between 1.7.6 and 1.14.0, and as such, any bug for any version between 1.7.6 and 1.14.0 will be fixed. This plugin is likely to work with many other Minecraft Spigot and Bukkit versions but this is untested and bugs found with these versions will not necessarily be fixed. 

## License: 
This plugin and its source code are released under the MIT license (see the [LICENSE file](https://github.com/aappleton8/OpPermissions/blob/master/LICENSE) for full details). This plugin is copyright (c) aappleton3/aappleton8, 2018 - 2019.  

## Build: 
This plugin is built using Eclipse. To build the plugin, the .classpath file firstly needs to be recreated using Eclipse, and the craftbukkit-1.12.2.jar file (or another version of craftbukkit) needs to be added as an external build dependency. 

## Commands: 
Most of the commands of this plugin do not work at all in command blocks and cannot be used by people without the correct permission, even if that player is an op. 

The general command syntaxes are: 
 - */opset &lt;arguments&gt;*. 
 - */oplist [online|offline|both]*. 
 - */oppermissions*. 
 - */oprequest &lt;message&gt;*. 
 - */opcommand &lt;arguments&gt;*.
 - */opbancommand &lt;arguments&gt;*.
 
Below, the individual commands are listed: 
 - /oppermissions - Show the help screen 
 - /opset help [1|2|3] - Show the help screen 
 - /opset help all [1|2|3] - Show help for all commands, even ones the player does not have permission to use 
 - /opset help config [1|2] - Show help for setting individual config file fields 
 - /opset version - Show the plugin version 
 - /opset add &lt;playername&gt; - Adds a player to the list 
 - /opset remove &lt;playername&gt; - Removes a player from the list 
 - /opset config reload - Reload the config file 
 - /opset config save - Save the config file 
 - /opset config set opscanop default|op|permission|no|false - Set if ops can use the */op* command 
 - /opset config set opscandeop default|op|permission|no|false - Set if ops can use the */deop* command 
 - /opset config set allowrequests op|permission|no - Enables or disables the */oprequest* command and sets which players see the request
 - /opset config set useuuids true|false - Set whether the plugin should use usernames or UUIDs 
 - /opset config set updateonplayerjoins true|false - Specify if the plugin should check user information each time the user joins 
 - /opset config set onlyautoupdateonline true|false - Specify whether the plugin should only automatically update information for online players or all players (choosing 'false' may perform operations on incorrect or non-existent players) 
 - /opset config set announceops all|permanent|normal|no|false - Specify of the plugin should announce when ops join 
 - /opset config set permanentopsusecommands true|false - Specify whether permanent ops can use all the blocked commands without the extra permission or not 
 - /opset config set showopattempts true|false - Specify whether players with the *oppermissions.showopattempts* permission get notified every time someone tries to use the */op* or */deop* command, even if not successfully 
 - /opset config set showcommanduse true|false - Specify whether players with the *oppermissions.command.show* permission get notified every time someone tries to use one of the commands on the list of blocked commands, even if not successfully 
 - /opset config set showbanattempts true|false - Specify whether players with the *oppermissions.ban.show* permission get notified every time someone tries to deop an op, even if not successfully 
 - /opset config set bannableops op|permanent|permission|default|no|false - Specify restrictions on which players can ban ops 
 - /opset config set bannablepermanentops op|permanent|permission|default|no|false - Specify additional restrictions on which players can ban permanent ops 
 - /opset config set deoponban true|false - Whether ops who are banned should be deopped or not 
 - /opset config verifylist - Update the ops list to convert between UUIDs and playernames, depending on the 'useuuids' config field and the 'onlyautoupdateonline' config field (this conversion will also happen when the 'useuuids' field is set) 
 - /opset list - List the list 
 - /opset check &lt;playername&gt; - Check if someone is on the list 
 - /oplist - List all the ops on the server, depending on the given permissions 
 - /oplist both - List both the online and offline ops 
 - /oplist online - List the online ops 
 - /oplist offline - List the offline ops 
 - /oprequest &lt;message&gt; - Send a message to the ops 
 - /opcommand add &lt;command&gt; - Block a command 
 - /opcommand remove &lt;command&gt; - Unblock a command 
 - /opcommand check &lt;command&gt; - Check if a command is blocked 
 - /opcommand list - List all the blocked commands 
 - /opbancommand add &lt;command&gt; - Watch a ban command 
 - /opbancommand remove &lt;command&gt; - Unwatch a ban command 
 - /opbancommand check &lt;command&gt; - Check if a ban command is watched 
 - /opbancommand list - List all the watched ban commands 
  
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
 - oppermissions.config.set.opscanop : The */opset config set opscanop default|op|permission|no|false* command | Default: false 
 - oppermissions.config.set.opscandeop : The */opset config set opscandeop default|op|permission|no|false* command | Default: false 
 - oppermissions.config.set.allowrequests : The */opset config set allowrequests op|permission|no* command | Default: false 
 - oppermissions.config.set.useuuids : The */opset config set useuuids true|false* command | Default: false 
 - oppermissions.config.set.updateonplayerjoins : The */opset config set updateonplayerjoins true|false* command | Default: false 
 - oppermissions.config.set.onlyautoupdateonline : The */opset config set onlyautoupdateonline true|false* command | Default: false
 - oppermissions.config.set.announceops : The */opset config set announceops all|permanent|normal|no|false* command | Default: false
 - oppermissions.config.set.permanentopsusecommands : The */opset config set permanentopsusecommands true|false* command | Default: false 
 - oppermissions.config.set.showopattempts : The */opset config set showopattempts true|false* command | Default: false
 - oppermissions.config.set.showcommanduse : The */opset config set showcommanduse true|false* command | Default: false
 - oppermissions.config.set.showbanattempts : The */opset config set showbanattempts true|false* command | Default: false
 - oppermissions.config.set.bannableops : The */opset config set bannableops op|permanent|permission|default|no|false* command | Default: false 
 - oppermissions.config.set.bannablepermanentops : The */opset config set bannablepermanentops op|permanent|permission|default|no|false* command | Default: false 
 - oppermissions.config.set.deoponban : Thw */opset config set deoponban true|false* command | Default: false 
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
 - oppermissions.command.add : Enable the player to add a command to the list of blocked commands | Default: false 
 - oppermissions.command.remove : Enable the player to remove a command from the list of blocked commands | Default: false 
 - oppermissions.command.check : Enable the player to check if a command is blocked | Default: false 
 - oppermissions.command.list : Enable the player to list all the blocked commands | Default: false 
 - oppermissions.command.command : Enable the player to use the blocked commands | Default: false 
 - oppermissions.command.show : Let the player be notified every time someone tries to use one of the commands on the blocked command list, even if it is unsuccessful | Default: false 
 - oppermissions.showopattempts : Let the player be notified every time someone tries to use the */op* or */deop* command, even if it is unsuccessful | Default: false 
 - oppermissions.ban.* : Let the player ban all ops and permanent ops, depending non the config file | Default: false
 - oppermissions.ban.permanentops : Let the player ban permanent ops depending on the config file | Default: false
 - oppermissions.ban.ops : Let the player ban ops depending on the config file | Default: false
 - oppermissions.ban.show : Let the player be notified everytime someone tries to ban an op or a permanent op | Default: false 
 - oppermissions.opbancommands.* : The root command to add and remove blocked commands | Default: false
 - oppermissions.opbancommands.add : Let the player watch a new ban command | Default: false
 - oppermissions.opbancommands.remove : Stop watching a previously watched ban command | Default: false
 - oppermissions.opbancommands.list : List the watched ban commands | Default: false
 - oppermissions.opbancommands.check : Check if a command is a watched ban command | Default: false

## Config: 
All configurable options for this plugin are in the 'config.yml' file. This file contains the following fields.  
 - useuuids - Sets whether the plugin should use usernames or UUIDs (true: use UUIDs; false: use usernames) 
 - updateonplayerjoins - Sets whether the plugin should check the information of a player each time it joins or not (true: check player information; false: don't check player information) 
 - onlyautoupdateonline - Sets whether the plugin should only allow opping, deopping and updating information of online players or online and offline players (there is no guarentee that the information changed for offline players will be correct) (true: online players only; false: online and offline players) 
 - allowrequests - Sets whether players can use the */oprequest* command or not (op: the requests are sent to ops; permission: the requests are sent to players with the permission *oppermissions.oprequest.see*; both: the requests are sent to ops and people with the permission *oppermissions.oprequest.see*; all: the requests are sent to everyone; no|false: requests cannot be made)  
 - opscanop - Sets whether ops can use the */op* command or not (default: the default happens; op: the player must be an op; permission: the player must have the *oppermissions.op* permission; no|false: the command can only be issued from the console)
 - opscandeop - Sets whether ops can use the */deop* command or not (default: the default happens; op: the player must be an op; permission: the player must have the *oppermissions.op* permission; no|false: the command can only be issued from the console)
 - announceops - Sets if the plugin should send a message to all players each time an op joins the server (all: the message is sent if any op joins; permanent: the message is sent if an op on the permanent ops list joins; normal: the message is sent if an op not on the permanent ops list joins; no|false: no message is sent) 
 - showopattempts - Whether players with the permission *oppermissions.showopattempts* are notified every time someone tried to use the */op* or */deop* command, even if it is unsuccessful (true: players with the permission are notified; false: no-one is notified)
 - permanentopsusecommands - Sets whether permanent ops can use the blocked commands even if they don't have the *oppermissions.command.command* permission or not (true: permanent ops can use the commands; false: permanent ops are treated the same as everyone else)
 - showcommanduse - Whether players with the permission *oppermissions.command.show* are notified every time someone tries to use one of the commands on the blocked command list, even if it is unsuccessful (true: players with the permission are notified; false: no-one is notified) 
 - bannableops - Whether ops are prevented from being banned or not (default: the default action happens; op: only ops can ban ops; permanent: only permanent ops can ban ops; permission: the *oppermissions.ban.ops* permission is required to ban ops; no|false: the command can only be performed by the console)
 - bannablepermanentops - Whether permanent ops are prevented from being banned or not (default: the default action happens; op: only ops can ban ops; permanent: only permanent ops can ban permanent ops; permission: the *oppermissions.ban.permanentops* permission is required to ban permanent ops; no|false: the command can only be performed by the console)
 - deoponban - Whether ops who are banned should be deopped or not (true: banned ops are deopped; false: banned ops are not deopped) 
 - showbanattempts - Whether to announce to people with the *oppermissions.ban.show* command when someone tries to ban an op or permanent op or not (true: announce op ban attempts; false: do not announce op ban attempts) 
 - ops - The list of permanent ops 
 - commands - The list of blocked commands 

The default config file (config.yml) is given below: 
```YAML
useuuids: true
updateonplayerjoins: true
onlyautoupdateonline: true
allowrequests: both
opscanop: default
opscandeop: default
announceops: no
showopattempts: true
permanentopsusecommands: true
showcommanduse: true
bannableops: default
bannablepermanentops: default
deoponban: false
showbanattempts: true
ops: 
  - aappleton3 
  - Codefined
commands:
  - stop
  - restart
  - reload
opbancommands:
  - ban
  - tempban
  - kick
  - blacklist
  - ip-ban
  - ban-ip
```



