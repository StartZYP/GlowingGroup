package com.qq44920040.Minecraft.GlowingGroup;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    private HashMap<UUID,Boolean> PlayerGroup = new HashMap<>();
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        Reload();
        super.onEnable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player&&label.equalsIgnoreCase("Glowing")&&args.length==1){
            if (Bukkit.getServer().getPlayer(args[0]).isOnline()){

            }else {
                sender.sendMessage("他不在线");
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerGroup.put(player.getUniqueId(),false);
        player.setGlowing(false);
    }
    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Player entity = event.getEntity();
        if (PlayerGroup.get(entity.getUniqueId())){
            PlayerGroup.put(entity.getUniqueId(),false);
            entity.setGlowing(false);
        }
    }


//    @EventHandler
//    public void PlayerDoCommand(Exe){
//
//    }

    private void Reload(){
        reloadConfig();

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
