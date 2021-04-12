package me.dkm.disguises

import me.dkm.disguises.models.DatabaseInfo
import me.dkm.disguises.models.DisguiseManager
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class Disguises : JavaPlugin() {

    private lateinit var disguiseManager: DisguiseManager;

    override fun onEnable() {
        INSTANCE = this
        saveDefaultConfig()
        disguiseManager = DisguiseManager(this, DatabaseInfo("", "", 0, "", "")) //todo fix
    }

    override fun onDisable() {
        disguiseManager.kickDisguised()
    }

    companion object {
        lateinit var INSTANCE: Disguises
    }
}