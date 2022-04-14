package fr.twah2em.login.commands

import fr.twah2em.login.Main
import fr.twah2em.login.encryption.PBKDF2Encryptor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * The register command.
 */
class RegisterCommand(private val main: Main) : CommandExecutor {

    override fun onCommand(player: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (player is Player) {
            if (args.size == 2) {
                if (args[0] != args[1]) {
                    player.sendMessage("§cLes mots de passe ne correspondent pas !")
                } else {
                    val encryptedValue = PBKDF2Encryptor().encrypt(args[0])

                    val encryptedValueInFile = main.dataFileUtil.get(player.name)

                    if (encryptedValueInFile != null) {
                        player.sendMessage("§cVous êtes déjà enregistré, veuillez utiliser la commande /login <password>")
                    } else {
                        main.dataFileUtil.set(player.name, encryptedValue)
                        player.sendMessage("§aVous avez été enregistré, veuillez utiliser la commande /login <password>")
                    }
                }
            } else {
                player.sendMessage("/register <password> <confirm password>")
            }
        } else {
            player.sendMessage("§cVous devez être un joueur pour utiliser cette commande.")
        }

        return true
    }
}