package de.minestar.library.utils.chat;

import java.util.Collection;

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import de.minestar.mscore.MSCore;


public class PlayerUtils {

    public static TextColor COLOR_PLUGIN_NAME = TextColors.AQUA;
    public static TextColor COLOR_INFO = TextColors.GRAY;
    public static TextColor COLOR_SUCCESS = TextColors.GREEN;
    public static TextColor COLOR_ERROR = TextColors.RED;
    public static TextColor COLOR_WARNING = TextColors.DARK_PURPLE;
    private static Server SPONGE_SERVER;

    // /////////////////////////////////////////////////////////////////////////////
    //
    // STATIC METHODS
    //
    //
    // /////////////////////////////////////////////////////////////////////////////

    static {
        SPONGE_SERVER = MSCore.getServer();
    }


    public static Server getServer() {
        return PlayerUtils.SPONGE_SERVER;
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    //
    // DATA-UNIT
    //
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static Player getOnlinePlayer(String partOfName) {
        // get all onlineplayers
        Collection<Player> players = getServer().getOnlinePlayers();

        // no player online
        if (players.size() < 1) {
            return null;
        }

        // search the player, with the closest match
        Player result = null;
        int delta = Integer.MAX_VALUE;
        int curDelta = Integer.MAX_VALUE;
        String tempName = "";
        partOfName = partOfName.toLowerCase();
        for (Player player : players) {
            // 1. check the playername first
            tempName = player.getName().toLowerCase();
            curDelta = tempName.length() - partOfName.length();
            if (curDelta < delta && tempName.contains(partOfName)) {
                delta = curDelta;
                result = player;
            }
            // TODO: check customname
            // else {
            // // 2. check the displayname
            // tempName = player.getDisplayNameData().getDisplayName().toLowerCase();
            // curDelta = tempName.length() - partOfName.length();
            // if (curDelta < delta && tempName.contains(partOfName)) {
            // delta = curDelta;
            // result = player;
            // }
            // }
            // player matches completely in length
            if (delta == 0) {
                return result;
            }
        }
        return result;
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    //
    // CHAT-UNIT
    //
    //
    // /////////////////////////////////////////////////////////////////////////////

    // /////////////////////////////////////////////////////////////////////////////
    //
    // STANDARD MESSAGES
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendMessage(Player player, Text message) {
        player.sendMessage(message);
    }


    public static void sendMessage(Player player, TextColor color, String message) {
        sendMessage(player, Texts.builder(message).color(color).build());
    }


    public static void sendMessage(Player player, String message) {
        sendMessage(player, TextColors.WHITE, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // ADVANCED MESSAGES
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendMessage(Player player, String pluginName, TextColor color, String message) {
        sendMessage(player, Texts.builder("[ " + pluginName + " ] ").color(COLOR_PLUGIN_NAME).append(Texts.builder(message).color(color).build()).build());
    }


    public static void sendMessage(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, TextColors.WHITE, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // INFO
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendInfo(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, COLOR_INFO, message);
    }


    public static void sendInfo(Player player, String message) {
        sendMessage(player, COLOR_INFO, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // SUCCESS
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendSuccess(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, COLOR_SUCCESS, message);
    }


    public static void sendSuccess(Player player, String message) {
        sendMessage(player, COLOR_SUCCESS, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // WARNING
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendWarning(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, COLOR_WARNING, message);
    }


    public static void sendWarning(Player player, String message) {
        sendMessage(player, COLOR_WARNING, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // ERROR
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendError(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, COLOR_ERROR, message);
    }


    public static void sendError(Player player, String message) {
        sendMessage(player, COLOR_ERROR, message);
    }
}
