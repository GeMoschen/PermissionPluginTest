package de.minestar.library.utils.chat;

import java.util.Collection;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;


public class PlayerUtils {

    private static Server SPONGE_SERVER;


    // /////////////////////////////////////////////////////////////////////////////
    //
    // STATIC METHODS
    //
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void initialize(final Game game) {
        SPONGE_SERVER = game.getServer();
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
        sendMessage(player, Texts.builder("[ " + pluginName + " ] ").color(TextColors.AQUA).append(Texts.builder(message).color(color).build()).build());
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
        sendMessage(player, pluginName, TextColors.GRAY, message);
    }


    public static void sendInfo(Player player, String message) {
        sendMessage(player, TextColors.GRAY, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // SUCCESS
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendSuccess(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, TextColors.GREEN, message);
    }


    public static void sendSuccess(Player player, String message) {
        sendMessage(player, TextColors.GREEN, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // WARNING
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendWarning(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, TextColors.DARK_PURPLE, message);
    }


    public static void sendWarning(Player player, String message) {
        sendMessage(player, TextColors.DARK_PURPLE, message);
    }


    // /////////////////////////////////////////////////////////////////////////////
    //
    // ERROR
    //
    // /////////////////////////////////////////////////////////////////////////////

    public static void sendError(Player player, String pluginName, String message) {
        sendMessage(player, pluginName, TextColors.RED, message);
    }


    public static void sendError(Player player, String message) {
        sendMessage(player, TextColors.RED, message);
    }
}
