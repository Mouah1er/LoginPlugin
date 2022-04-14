package fr.twah2em.login

import fr.twah2em.login.commands.LoginCommand
import fr.twah2em.login.commands.RegisterCommand
import fr.twah2em.login.encryption.file.DataFileUtils
import fr.twah2em.login.listeners.PlayerQuitListener
import fr.twah2em.login.tasks.LoginTask
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.UUID

/**
 * Main class of the plugin
 */
class Main : JavaPlugin() {
    val connectedPlayers = mutableListOf<UUID>()
    val dataFile: File = File(dataFolder, "data.json")
    val dataFileUtil: DataFileUtils = DataFileUtils(this)

    override fun onEnable() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }

        LoginTask(this).runTaskTimer(this, 0, 40)

        server.pluginManager.registerEvents(PlayerQuitListener(this), this)

        getCommand("login")!!.setExecutor(LoginCommand(this))
        getCommand("register")!!.setExecutor(RegisterCommand(this))
    }
}