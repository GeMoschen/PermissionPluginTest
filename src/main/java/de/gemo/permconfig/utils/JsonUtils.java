package de.gemo.permconfig.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by GeMo on 17.05.2015.
 */
public class JsonUtils {

    public final static String JSON_GROUPS = "groups";
    public final static String JSON_PLAYERS = "players";
    public final static String JSON_PARENT = "parent";
    public final static String JSON_WHITELIST = "whitelist";
    public final static String JSON_BLACKLIST = "blacklist";

    public static String prettyFormat(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonElement);
        return json;
    }

}
