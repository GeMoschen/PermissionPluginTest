package de.minestar.library.utils.log;

import de.Log;
import de.minestar.library.plugin.AbstractCore;


public class LogUtils {

    public static void DEBUG(AbstractCore plugin, String message) {
        Log.DEBUG("[ " + plugin.getName() + " ] " + message);
    }


    public static void ERROR(AbstractCore plugin, String message) {
        Log.ERROR("[ " + plugin.getName() + " ] " + message);
    }


    public static void INFO(AbstractCore plugin, String message) {
        Log.INFO("[ " + plugin.getName() + " ] " + message);
    }


    public static void WARN(AbstractCore plugin, String message) {
        Log.WARN("[ " + plugin.getName() + " ] " + message);
    }
}
