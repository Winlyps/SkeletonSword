package winlyps.skeletonSword

import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import winlyps.skeletonSword.commands.ReloadCommand

class SkeletonSword : JavaPlugin() {
    
    private val weaponDamage = mutableMapOf<Material, Double>()
    private var enableCustomDamage = true
    private var debug = false

    override fun onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig()
        
        // Load configuration
        loadConfig()
        
        // Register the event listeners
        server.pluginManager.registerEvents(SkeletonSpawnListener(this), this)
        server.pluginManager.registerEvents(SkeletonDamageListener(this), this)
        
        // Register commands
        getCommand("skeletonswordreload")?.setExecutor(ReloadCommand(this))
        
        // Log startup message
        logger.info("SkeletonSword has been enabled with custom damage ${if (enableCustomDamage) "enabled" else "disabled"}")
        if (debug) {
            logger.info("Weapon damage values: $weaponDamage")
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("SkeletonSword has been disabled")
    }
    
    /**
     * Loads configuration values from config.yml
     */
    private fun loadConfig() {
        // Reload config from file
        reloadConfig()
        
        // Clear existing damage values
        weaponDamage.clear()
        
        // Load weapon damage values
        val configSection = config.getConfigurationSection("weapon-damage")
        configSection?.getKeys(false)?.forEach { key ->
            try {
                val material = Material.valueOf(key.uppercase())
                val damage = configSection.getDouble(key)
                weaponDamage[material] = damage
            } catch (e: IllegalArgumentException) {
                logger.warning("Invalid material in config: $key")
            }
        }
        
        // Load other settings
        enableCustomDamage = config.getBoolean("enable-custom-damage", true)
        debug = config.getBoolean("debug", false)
    }
    
    /**
     * Gets the custom damage value for a specific sword material
     * 
     * @param material The sword material
     * @return The custom damage value, or null if not configured
     */
    fun getWeaponDamage(material: Material): Double? {
        if (!enableCustomDamage) return null
        return weaponDamage[material]
    }
    
    /**
     * Checks if custom damage is enabled
     * 
     * @return true if custom damage is enabled
     */
    fun isCustomDamageEnabled(): Boolean {
        return enableCustomDamage
    }
    
    /**
     * Checks if debug mode is enabled
     * 
     * @return true if debug mode is enabled
     */
    fun isDebugEnabled(): Boolean {
        return debug
    }
    
    /**
     * Reloads the plugin configuration
     */
    fun reloadPluginConfig() {
        loadConfig()
        logger.info("Configuration reloaded")
    }
}
