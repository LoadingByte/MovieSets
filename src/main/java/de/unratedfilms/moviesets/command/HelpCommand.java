
package de.unratedfilms.moviesets.command;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandExecutor;
import com.quartercode.quarterbukkit.api.command.CommandHandler;
import com.quartercode.quarterbukkit.api.command.CommandInfo;

public class HelpCommand implements CommandHandler {

    @Override
    public CommandInfo getInfo() {

        return new CommandInfo(true, null, "Shows a help page.", "moviesets.command.help", "<empty>", "help");
    }

    @Override
    public void execute(Command command) {

        CommandSender sender = command.getSender();

        sender.sendMessage(ChatColor.GREEN + "==========[ MovieSets Help ]==========");

        PluginCommand registeredCommand = Bukkit.getPluginCommand("moviesets");
        String aliases = ChatColor.DARK_GREEN + "/" + registeredCommand.getLabel() + ChatColor.AQUA + ", ";
        for (String alias : registeredCommand.getAliases()) {
            aliases += ChatColor.DARK_GREEN + "/" + alias + ChatColor.AQUA + ", ";
        }
        aliases = aliases.substring(0, aliases.length() - 2);
        sender.sendMessage("Aliases: " + ChatColor.DARK_GREEN + aliases);

        List<CommandHandler> commandHandlers = ((CommandExecutor) registeredCommand.getExecutor()).getCommandHandlers();
        for (CommandHandler commandHandler : commandHandlers) {
            CommandInfo info = commandHandler.getInfo();

            if (! (sender instanceof Player) || info.getPermission() == null || ((Player) sender).hasPermission(info.getPermission())) {
                for (String label : info.getLabels()) {
                    String printLabel = "";
                    if (!label.equalsIgnoreCase("<empty>")) {
                        printLabel = " " + label;
                    }

                    String parameterUsage = "";
                    if (info.getParameterUsage() != null && !info.getParameterUsage().isEmpty()) {
                        parameterUsage = " " + info.getParameterUsage();
                    }

                    sender.sendMessage(ChatColor.GOLD + "/" + command.getGlobalLabel() + printLabel + parameterUsage);
                }

                sender.sendMessage(ChatColor.DARK_RED + "  > " + ChatColor.GRAY + info.getDescription());
            }
        }
    }

}
