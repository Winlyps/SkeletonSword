package winlyps.skeletonSword

import org.bukkit.entity.EntityType
import org.bukkit.entity.Skeleton
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class SkeletonDamageListener(private val plugin: SkeletonSword) : Listener {

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        // Check if the damager is a skeleton
        if (event.damager.type == EntityType.SKELETON) {
            val skeleton = event.damager as Skeleton
            val mainHandItem = skeleton.equipment?.itemInMainHand
            
            // Check if the skeleton is holding a sword
            if (mainHandItem != null && mainHandItem.type.name.endsWith("_SWORD")) {
                // Get the custom damage value for this sword type
                val customDamage = plugin.getWeaponDamage(mainHandItem.type)
                
                // Apply the custom damage if configured
                if (customDamage != null) {
                    val originalDamage = event.damage
                    event.damage = customDamage
                    
                    // Debug logging
                    if (plugin.isDebugEnabled()) {
                        plugin.logger.info("Skeleton damage with ${mainHandItem.type.name}: $originalDamage → $customDamage")
                    }
                }
            }
        }
    }
}
