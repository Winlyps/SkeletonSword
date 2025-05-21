package winlyps.skeletonSword.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import winlyps.skeletonSword.SkeletonSword

class ReloadCommand(private val plugin: SkeletonSword) : CommandExecutor {
    
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("skeletonsword.reload")) {
            sender.sendMessage("${ChatColor.RED}You don't have permission to use this command.")
            return true
        }
        
        try {
            plugin.reloadPluginConfig()
            sender.sendMessage("${ChatColor.GREEN}SkeletonSword configuration reloaded successfully!")
        } catch (e: Exception) {
            sender.sendMessage("${ChatColor.RED}Failed to reload configuration: ${e.message}")
            plugin.logger.severe("Error reloading configuration: ${e.message}")
            e.printStackTrace()
        }
        
        return true
    }
}