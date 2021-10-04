package xyz.sqlskid.ipwl.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import xyz.sqlskid.ipwl.IPWhitelist;

import java.util.UUID;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerLoginEvent e){
        String ip = e.getAddress().getHostAddress();
        UUID uuid = e.getPlayer().getUniqueId();

        if(IPWhitelist.instance.ipDatabase.containsKey(uuid)){
            if(IPWhitelist.instance.ipDatabase.get(uuid).equals("")){
                IPWhitelist.instance.ipDatabase.put(uuid, ip);
            }
            else if(!IPWhitelist.instance.ipDatabase.get(uuid).equals(ip)){
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to join this server.");
            }else if(IPWhitelist.instance.ipDatabase.get(uuid).equals(ip)){
                e.allow();
            }
        }else{
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to join this server.");
        }
    }

}
