package io.github._202e.realdice.commands;

import io.github._202e.realdice.DiceRoll;
import io.github._202e.realdice.RealDice;
import io.github._202e.realdice.exception.RollParseException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRoll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Didn't specify formula for dice roll");
            return false;
        } else if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Too many arguments. Do not use spaces in dice roll formula.");
            return false;
        }
        try {
            int[] outcome = DiceRoll.rollFormat(args[0]);
            String msg = ChatColor.DARK_PURPLE + sender.getName() + " rolled: " + DiceRoll.formatOutcome(outcome, args[0]);
            if (sender instanceof Player) {
                Player p = (Player) sender;
                for (Player receiver : p.getWorld().getPlayers()) {
                    if (p.getLocation().distance(receiver.getLocation()) <= RealDice.broadcastDistance) {
                        receiver.sendMessage(msg);
                    }
                }
            } else {
                sender.sendMessage(msg);
            }
            return true;
        } catch (RollParseException rpe) {
            sender.sendMessage(ChatColor.RED + "Couldn't parse dice roll: " + rpe.problem);
            return false;
        }
    }
}
