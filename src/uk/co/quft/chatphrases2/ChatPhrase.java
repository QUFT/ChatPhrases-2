package uk.co.quft.chatphrases2;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * ChatPhrase class stores all methods for ChatPhrase objects.
 *
 * @author ZeWildGuy
 */
public class ChatPhrase {

    private static Plugin plugin = ChatPhrases.plugin;
    public static String errorPhrase = ChatPhrases.errorPhrase;

    private static HashMap<String, String> Phrases = new HashMap<String, String>();

    private static String prepareError(String phrase_id) {
        if(ChatPhrases.useErrorPhrase) {
            return errorPhrase;
        } else {
            return phrase_id;
        }
    }

    public static String match(String phrase_to_match) {
        boolean match = false;

        for(String phrase_key : Phrases.keySet()) {
            if(phrase_key.equalsIgnoreCase(phrase_to_match)) {
                match = true;
                return phrase_key;
            }
        }

        if(!match) {
            return null;
        }
        return null;
    }

    public static String getValue(String phrase_id_key) {

        String value = Phrases.get(phrase_id_key);

        return value;
    }

    public static String replaceVariables(String formatted_phrase, HashMap<String, String> map_of_variables) {

        for(String key : map_of_variables.keySet()) {
            formatted_phrase = formatted_phrase.replaceAll("%" + key + "%", map_of_variables.get(key));
        }

        return formatted_phrase;
    }

    /**
     * Looks for and retrieves a phrase with the specified id.
     * Also replaces variables with the values specified in the map.
     *
     * @author ZeWildGuy
     * @param requested_phrase_id The phrase that is requested.
     * @param map_of_variabes A map of variables and their values.
     * @return The final phrase fully formatted.
     */
    public static String getPhrase(String requested_phrase_id, HashMap<String, String> map_of_variabes) {

        String phrase_key = match(requested_phrase_id);

        if(phrase_key != null) {
            String phrase_value = getValue(phrase_key);
            String final_phrase_value = replaceVariables(phrase_value, map_of_variabes); //replaces variable names with content

            String final_phrase = ChatFormat.parseChat(final_phrase_value);
            return final_phrase;

        } else {
            return prepareError(requested_phrase_id);
        }
    }

    /**
     * Looks for and retrieves a phrase with the specified id.
     * Does not accept a map of variables to be replaced.
     *
     * @author ZeWildGuy
     * @param requested_phrase_id The phrase that is requested.
     * @return The final phrase fully formatted.
     */
    public static String getPhrase(String requested_phrase_id) {

        String phrase_key = match(requested_phrase_id);

        if(phrase_key != null) {
            String phrase_value = getValue(phrase_key);

            String final_phrase = ChatFormat.parseChat(phrase_value);
            return final_phrase;

        } else {
            return prepareError(requested_phrase_id);
        }
    }

    /**
     * Adds a phrase directly to the phrase list without registering it in the plugin.yml file.
     * <b>It is not recommended that this method is used.</b>
     *
     * @author ZeWildGuy
     * @param phrase_id
     * @param phrase
     */
    public static void add(String phrase_id, String phrase) {
        Phrases.put(phrase_id, phrase);
    }

    /**
     * Registers a phrase to be retrieved from the plugin.yml file.
     * If the phrase does not exist it will be added to the file.
     *
     * @author ZeWildGuy
     * @param phrase_id
     */
    public static void register(String phrase_id, String default_phrase_value) {
        if(plugin.getConfig().isSet("phrases." + phrase_id)) {
            String phrase = plugin.getConfig().getString("phrases." + phrase_id);
            Phrases.put(phrase_id, phrase);
        } else {
            plugin.getConfig().set("phrases." + phrase_id, default_phrase_value);
            Phrases.put(phrase_id, default_phrase_value);
        }
    }
}
