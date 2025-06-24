package winlyps.skeletonSword

import org.bukkit.entity.AbstractSkeleton
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class SkeletonDamageListener(private val plugin: SkeletonSword) : Listener {

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        // Check if the damager is a skeleton, stray, or bogged
        if (event.damager is AbstractSkeleton) {
            val skeleton = event.damager as AbstractSkeleton
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
                        plugin.logger.info("${skeleton.type} damage with ${mainHandItem.type.name}: $originalDamage → $customDamage")
                    }
                }
            }
        }
    }
}
