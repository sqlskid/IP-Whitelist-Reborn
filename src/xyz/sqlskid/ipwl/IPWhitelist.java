package xyz.sqlskid.ipwl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectOutputStream;
import xyz.sqlskid.ipwl.commands.IPWLCommand;
import xyz.sqlskid.ipwl.listeners.JoinLeaveListener;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class IPWhitelist extends JavaPlugin
{

    public static IPWhitelist instance;

    public String prefix = "§7[§aIP-Whitelist§7]§f";

    public HashMap<UUID, String> ipDatabase = new HashMap<>();

    private File dataFile = new File(getDataFolder(), "main.dat");


    public void onEnable()
    {
        instance = this;

        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Data file does not exist! Creating new data file...");
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Could not create a new file.");
                e.printStackTrace();
            }
        }

        Bukkit.getPluginCommand("ipwhitelist").setExecutor(new IPWLCommand());
        Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(), this);

        try {
            readIPs();
            Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Successfully loaded " + ipDatabase.size() + " IPs!");;
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Unable to open data file!");;
            e.printStackTrace();
        }

    }

    public void onDisable()
    {
        try {
            saveIPs();
            Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Successfully saved " + ipDatabase.size() + " IPs!");
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[IP-Whitelist] Unable to open data file!");;
            e.printStackTrace();
        }

    }

    private void saveIPs() throws IOException {
        FileWriter writer = new FileWriter(dataFile);
        for(UUID uuid: ipDatabase.keySet()){
            String ip = ipDatabase.get(uuid);
            if(!ip.equals("")){
                writer.write(uuid.toString() + ":" + ip);
            }
        }
        writer.close();

    }

    private void readIPs() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;
        while ((line = reader.readLine()) != null){
            String[] raw = line.split(":");
            ipDatabase.put(UUID.fromString(raw[0]), raw[1]);
        }
        reader.close();
    }


}
