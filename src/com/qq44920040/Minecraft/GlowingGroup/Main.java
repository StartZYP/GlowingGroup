package com.qq44920040.Minecraft.GlowingGroup;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
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
        if (sender instanceof Player&&label.equalsIgnoreCase("dkf")){


            Player cmdplayer = Bukkit.getServer().getPlayer(args[0]);
            if (args.length==1&&Bukkit.getServer().getPlayer(args[0]).isOnline()){
                PlayerGroup.put(cmdplayer.getUniqueId(),true);
                cmdplayer.setGlowing(true);
                sender.sendMessage("成功给他丢到第二组");
            }else {
                sender.sendMessage("他不在线");
            }

            if (args.length==1&&args[0].equalsIgnoreCase("list")){
                Set<UUID> uuids = PlayerGroup.keySet();
                for (UUID uuid:uuids){
                    if (PlayerGroup.get(uuid).booleanValue()){
                        sender.sendMessage("玩家名:"+Bukkit.getServer().getPlayer(uuid).getName());
                    }
                }
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


    @EventHandler
    public void PlayerDoCommand(PlayerCommandPreprocessEvent event){
        if (event.getMessage().contains("nuke")){
            Player player =event.getPlayer();
            if (!PlayerGroup.get(player.getUniqueId())){
                event.setCancelled(true);
                player.sendMessage("您的信息已经被拦截");
            }
        }
    }

    private void Reload(){
        reloadConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
