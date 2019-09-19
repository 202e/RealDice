package io.github._202e.realdice;

import org.bukkit.plugin.java.JavaPlugin;

public class RealDice extends JavaPlugin {
    public static int broadcastDistance;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        RealDice.broadcastDistance = getConfig().getInt("rollbroadcastdistance");
    }
}
