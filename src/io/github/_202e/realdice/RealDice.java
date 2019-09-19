package io.github._202e.realdice;

import io.github._202e.realdice.commands.CommandRoll;
import org.bukkit.plugin.java.JavaPlugin;

public class RealDice extends JavaPlugin {
    public static int broadcastDistance;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        RealDice.broadcastDistance = getConfig().getInt("rollbroadcastdistance");

        getCommand("roll").setExecutor(new CommandRoll());
    }
}
