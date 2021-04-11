package me.dkm.disguises

import me.dkm.disguises.models.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Disguises : JavaPlugin(), Listener {

    private lateinit var sqlManager: SQLManager;
    private lateinit var disguiseManager: DisguiseManager;

    override fun onEnable() {
        saveDefaultConfig()
        sqlManager = SQLManager(this)
        disguiseManager = DisguiseManager(this)
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        disguiseManager.kickDisguised()
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        e.joinMessage = null
        e.player.displayName = e.player.name
        val disguise = sqlManager.getDisguise(e.player) ?: return
        if(!disguise.active) return
        disguiseManager.disguise(e.player, disguise, true)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        e.quitMessage = null
        disguiseManager.removePlayer(e.player)
    }

    override fun onCommand(plr: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if(plr !is Player || !plr.hasPermission("disguise.use")) return false

        when(command.name) {
            "disguise" -> disguise(plr, args)
            "undisguise" -> undisguise(plr)
            "redisguise" -> redisguise(plr)
            "name" -> plr.sendMessage("Your name is ${plr.name}")
        }
        return true
    }

    private fun redisguise(plr: Player) {
        val dis = sqlManager.getDisguise(plr) ?: return
        if(dis.active) return
        sqlManager.setActivityState(dis, true)
        disguiseManager.disguise(plr, dis, false)
        plr.sendMessage("${ChatColor.GREEN}You have been re-disguised.")
    }

    private fun undisguise(plr: Player) {
        val dis = sqlManager.getDisguise(plr) ?: return
        if(!dis.active) return
        sqlManager.setActivityState(dis, false)
        disguiseManager.undisguise(plr)
        plr.sendMessage("${ChatColor.GREEN}Your disguise has been removed.")
    }

    private fun disguise(plr: Player, args: Array<String>) {
        val d = sqlManager.getDisguise(plr)
        if(d != null && d.active) return // already disguised
        var username = ""
        if(args.isNotEmpty()) {
            username = args[0]
            if(username.length > 16)
                username = username.substring(0, 16)
        }
        when (args.size) {
            0 -> return
            1 -> {
                disguiseManager.httpUtility.getTextureAndSignature(username) { texture: String?, signature: String? ->
                    if(texture != null && signature != null)
                        setupDisguise(plr, username, Skin(texture, signature))
                    else
                        setupDisguise(plr, username, if(Math.random() > 0.5) PremadeSkin.STEVE else PremadeSkin.ALEX)
                }
            }
            else -> {
                var playerSkin: String? = null
                var rank: Rank? = null
                var premadeSkin: PremadeSkin? = null
                for(i in 1 until args.size) {
                    if(!args[i].contains("="))
                        playerSkin = args[i]
                    else {
                        val arr = args[i].split("=")
                        if(arr.size != 2) continue
                        when(arr[0].toLowerCase()) {
                            "rank" -> rank = Rank.of(arr[1].toUpperCase())
                            "skin" -> premadeSkin = PremadeSkin.of(arr[1].toUpperCase())
                        }
                    }
                }
                if(playerSkin != null) {
                    disguiseManager.httpUtility.getTextureAndSignature(playerSkin) { texture: String?, signature: String? ->
                        if(texture != null && signature != null)
                            setupDisguise(plr, username, Skin(texture, signature), rank)
                        else
                            setupDisguise(plr, username, if(Math.random() > 0.5) PremadeSkin.STEVE else PremadeSkin.ALEX, rank)
                    }
                } else if (premadeSkin != null) {
                    setupDisguise(plr, username, premadeSkin)
                } else {
                    disguiseManager.httpUtility.getTextureAndSignature(username) { texture: String?, signature: String? ->
                        if(texture != null && signature != null)
                            setupDisguise(plr, username, Skin(texture, signature), rank)
                        else
                            setupDisguise(plr, username, if(Math.random() > 0.5) PremadeSkin.STEVE else PremadeSkin.ALEX, rank)
                    }
                }

            }
        }
    }

    fun setupDisguise(plr: Player, name: String, skin: ISkin) {
        setupDisguise(plr, name, skin, Rank.VIP)
    }


    fun setupDisguise(plr: Player, name: String, skin: ISkin, rank: Rank?) {
        val disguise = Disguise(
                true,
                name,
                plr.name,
                plr.uniqueId,
                plr.uniqueId,
                skin,
                disguiseManager.convertSkin(plr),
                rank ?: Rank.VIP
        )
        sqlManager.addDisguise(disguise)
        disguiseManager.disguise(plr, disguise, false)
    }
}