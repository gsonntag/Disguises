package me.dkm.disguises.models

import com.mojang.authlib.GameProfile
import me.dkm.disguises.Disguises
import me.dkm.disguises.utility.HTTPUtility
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.Node
import net.luckperms.api.node.types.PrefixNode
import net.minecraft.server.v1_8_R3.*
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList

class DisguiseManager(val plugin: Disguises) {

    private val activeDisguises = mutableListOf<Disguise>()

    val httpUtility = HTTPUtility(plugin)

    fun disguise(player: Player, disguise: Disguise, join: Boolean) {
        if(!join) {
            val lp = LuckPermsProvider.get()
            val user = lp.userManager.getUser(player.uniqueId) ?: return
            user.data().add(PrefixNode.builder(disguise.rank.prefix, 100).build())
        }
        object : BukkitRunnable() {
            override fun run() {
                val profile = getProfile(player)
                setName(profile, disguise.name)
                setId(profile, disguise.id)
                player.displayName = disguise.name
                removeSkin(profile)
                setSkin(profile, disguise.skin)
                sendOnTheFlyPackets(player as CraftPlayer, !join)
                activeDisguises.add(disguise)
            }
        }.runTaskLater(plugin, 2)
    }

    private fun getProfile(player: Player): GameProfile {
        return (player as CraftPlayer).profile
    }

    fun undisguise(player: Player) {
        val disguise = getDisguise(player) ?: return
        val profile = getProfile(player)
        setName(profile, disguise.realName)
        player.displayName = disguise.realName
        setId(profile, disguise.realId)
        removeSkin(profile)
        setSkin(profile, disguise.realSkin)
        sendOnTheFlyPackets(player as CraftPlayer, true)
        activeDisguises.remove(disguise)
        object : BukkitRunnable() {
            override fun run() {
                val lp = LuckPermsProvider.get()
                val user = lp.userManager.getUser(player.uniqueId) ?: return
                user.data().remove(PrefixNode.builder(disguise.rank.prefix, 100).build())
            }
        }.runTaskLater(plugin, 2)
    }

    private fun getDisguise(player: Player): Disguise? {
        activeDisguises.forEach {
            if(it.id == player.uniqueId)
                return it
        }
        return null
    }

    private fun setName(profile: GameProfile, name: String) {
        nameField.isAccessible = true
        nameField.set(profile, name)
        nameField.isAccessible = false
    }

    private fun setId(profile: GameProfile, id: UUID) {
        idField.isAccessible = true
        idField.set(profile, id)
        idField.isAccessible = false
    }

    private fun removeSkin(profile: GameProfile) {
        profile.properties.removeAll("textures")
    }

    private fun setSkin(profile: GameProfile, skin: ISkin) {
        profile.properties.put("textures", skin.toProperty())
    }

    private fun sendOnTheFlyPackets(craftPlayer: CraftPlayer, sendDestroySpawnPackets: Boolean) {
        val entityPlayer = craftPlayer.handle
        val add = PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer)
        val remove = PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer)
        val destroy = PacketPlayOutEntityDestroy(entityPlayer.id)
        val spawn = PacketPlayOutNamedEntitySpawn(entityPlayer)
        val respawn = PacketPlayOutRespawn(
                entityPlayer.world.worldProvider.dimension,
                entityPlayer.world.difficulty,
                entityPlayer.world.worldData.type,
                WorldSettings.EnumGamemode.getById(craftPlayer.gameMode.value)
        )
        val location = craftPlayer.location
        val position = PacketPlayOutPosition(
                location.x, location.y, location.z, location.yaw, location.pitch, setOf<PacketPlayOutPosition.EnumPlayerTeleportFlags>()
        )
        val slot = PacketPlayOutHeldItemSlot(
                craftPlayer.inventory.heldItemSlot
        )
        Bukkit.getOnlinePlayers().forEach {
            (it as CraftPlayer).handle.playerConnection.sendPacket(remove)
            if(sendDestroySpawnPackets) it.handle.playerConnection.sendPacket(destroy)
        }
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getOnlinePlayers().forEach {
                    (it as CraftPlayer).handle.playerConnection.sendPacket(add)
                    if(sendDestroySpawnPackets && it.handle != entityPlayer)
                        it.handle.playerConnection.sendPacket(spawn)
                }
                if(sendDestroySpawnPackets) {
                    entityPlayer.playerConnection.sendPacket(respawn)
                    entityPlayer.playerConnection.sendPacket(position)
                    entityPlayer.playerConnection.sendPacket(slot)
                }
            }
        }.runTaskLater(plugin, 5)
    }

    fun kickDisguised() {
        activeDisguises.forEach {
            Bukkit.getPlayer(it.id)?.kickPlayer("Sorry, but disguised players must relog at this time.")
        }
    }

    fun removePlayer(plr: Player) {
        ArrayList(activeDisguises).forEach {
            if(plr.uniqueId == it.id)
                activeDisguises.remove(it)
        }
    }

    fun convertSkin(plr: Player): ISkin {
        val profile = (plr as CraftPlayer).profile
        val list = profile.properties.get("textures");
        if(list.isEmpty()) throw RuntimeException("Textures list is empty")
        val skin = list.iterator().next()
        return Skin(skin.value, skin.signature)
    }

    companion object {
        private val nameField = GameProfile::class.java.getDeclaredField("name")
        private val idField = GameProfile::class.java.getDeclaredField("id")
    }

}
