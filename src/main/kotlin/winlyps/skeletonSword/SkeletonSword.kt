package winlyps.skeletonSword

import org.bukkit.plugin.java.JavaPlugin

class SkeletonSword : JavaPlugin() {

    override fun onEnable() {
        // Register the event listener
        server.pluginManager.registerEvents(SkeletonSpawnListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}