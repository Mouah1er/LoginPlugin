package fr.twah2em.login.listeners

import fr.twah2em.login.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * PlayerQuitListener to handle player quit event
 */
class PlayerQuitListener(private val main: Main) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerQuitEvent) {
        main.connectedPlayers.remove(event.player.uniqueId)
    }
}