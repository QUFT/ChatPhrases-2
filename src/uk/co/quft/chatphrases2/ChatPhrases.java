package uk.co.quft.chatphrases2;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class ChatPhrases extends JavaPlugin {

    public static Plugin plugin;

    public static final Logger log = Logger.getLogger("Minecraft");

    public static final String version = "2.0.0";
    public static String errorPhrase;
    public static boolean useErrorPhrase;

    @Override
    public void onDisable() {
        log.info("[ChatPhrases] Shutting down ChatPhrases v" + version);
    }

    @Override
    public void onEnable() {

        log.info("[ChatPhrases] Preparing to start ChatPhrases v" + version);
        plugin = this;

        log.info("[ChatPhrases] Preparing to configure ChatPhrases");
        this.saveDefaultConfig();

        errorPhrase = this.getConfig().getString("settings.error-message");
        useErrorPhrase = this.getConfig().getBoolean("settings.use-error-message");

        log.info("[ChatPhrases] Preparing phrases");
        List<String> listOfStrings = this.getConfig().getStringList("phrases");

        ChatPhrase.add("phrase_id", "phrase_content");

        for(String phrase : listOfStrings) {
            //String[] splits = phrase.split("%b", 2);
            String[] temp;
            temp = phrase.split("%b%", 2);

            String phrase_id = temp[0];
            String phrase_content = temp[1];

            ChatPhrase.add(phrase_id, phrase_content);

        }

        log.info("[ChatPhrases] " + version + " is now running!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("chatphrases")) {
            if (args.length >= 2) {
                sender.sendMessage(ChatPhrase.getPhrase("no_valid_arguments"));
                return false;
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(sender.hasPermission("chatphrases.reload")) {
                    sender.sendMessage(ChatPhrase.getPhrase("reloading_plugin"));
                    this.reloadConfig();
                    this.onEnable();
                } else {
                    sender.sendMessage(ChatPhrase.getPhrase("no_permission"));
                }
            } else {

            }
            return true;
        }
        return false;
    }
}
