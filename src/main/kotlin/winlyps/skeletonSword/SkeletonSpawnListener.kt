package winlyps.skeletonSword

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Skeleton
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.Random

class SkeletonSpawnListener(private val plugin: JavaPlugin) : Listener {

    private val random = Random()

    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        if (event.entityType == EntityType.SKELETON) {
            val skeleton = event.entity as Skeleton
            val randomSword = getRandomSword()
            skeleton.equipment?.setItemInMainHand(ItemStack(randomSword))
            skeleton.equipment?.helmet = null
            skeleton.equipment?.chestplate = null
            skeleton.equipment?.leggings = null
            skeleton.equipment?.boots = null
        }
    }

    private fun getRandomSword(): Material {
        val randomValue = random.nextDouble()
        return when {
            randomValue < 0.01 -> Material.NETHERITE_SWORD
            randomValue < 0.04 -> Material.DIAMOND_SWORD
            randomValue < 0.11 -> Material.GOLDEN_SWORD
            randomValue < 0.26 -> Material.IRON_SWORD
            randomValue < 0.63 -> Material.STONE_SWORD
            else -> Material.WOODEN_SWORD
        }
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entityType == EntityType.SKELETON) {
            event.drops.removeIf { it.type == Material.BOW || it.type == Material.ARROW }
            val skeleton = event.entity as Skeleton
            val mainHandItem = skeleton.equipment?.itemInMainHand
            if (mainHandItem != null && mainHandItem.type.name.endsWith("_SWORD")) {
                val brokenSword = ItemStack(mainHandItem.type, 1)
                val maxDurability = brokenSword.type.maxDurability
                val durabilityRange = (maxDurability - 15)..(maxDurability - 5)
                brokenSword.durability = durabilityRange.random().toShort()
                event.drops.add(brokenSword)
            }
        }
    }
}