package fr.twah2em.login.tasks

import fr.twah2em.login.Main
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * Task to ask the player to login
 */
class LoginTask(private val main: Main) : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers().forEach { player ->
            if (main.connectedPlayers.contains(player.uniqueId)) {
                return
            }

            player.sendMessage("§c/login <mot de passe> pour vous connecter !")
            player.sendMessage("§c/register <mot de passe> pour vous inscrire !")
        }
    }
}