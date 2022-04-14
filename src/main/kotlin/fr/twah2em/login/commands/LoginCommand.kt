package fr.twah2em.login.commands

import fr.twah2em.login.Main
import fr.twah2em.login.encryption.PBKDF2Encryptor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * The login command.
 */
class LoginCommand(private val main: Main) : CommandExecutor {

    override fun onCommand(player: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (player is Player) {
            if (main.connectedPlayers.contains(player.uniqueId)) {
                player.sendMessage("§cVous êtes déjà connecté !")
            } else {
                if (args.size == 1) {
                    val encryptedValueInFile = main.dataFileUtil.get(player.name)

                    if (encryptedValueInFile != null && encryptedValueInFile.isNotEmpty() && PBKDF2Encryptor().validate(
                            args[0], encryptedValueInFile
                        )
                    ) {
                        player.sendMessage("§aVous êtes connecté !")
                        main.connectedPlayers.add(player.uniqueId)
                    } else {
                        player.sendMessage(
                            "§cLe mot de passe n'est pas correct, veuillez utiliser la commande /register <password> <confirm password>" +
                                    " pour vous inscrire."
                        )
                    }
                } else {
                    player.sendMessage("/login <password>")
                }
            }
        } else {
            player.sendMessage("§cVous devez être un joueur pour utiliser cette commande.")
        }

        return true
    }
}