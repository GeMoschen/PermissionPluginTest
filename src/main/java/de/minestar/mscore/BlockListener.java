package de.minestar.mscore;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerPlaceBlockEvent;

import de.Log;
import de.minestar.library.utils.chat.PlayerUtils;


public class BlockListener {

    @Subscribe
    public void onBlockPlace(PlayerPlaceBlockEvent event) {
        Log.INFO(event.getClass().getSimpleName());

        Player player = event.getUser();
        if (player.hasPermission("player.canPlace")) {
            PlayerUtils.sendInfo(player, "BlockPlace OLD", event.getReplacementBlock().getState().getType().getName());
            PlayerUtils.sendInfo(player, "BlockPlace NEW", event.getBlock().getType().getName());
        } else {
            event.setCancelled(true);
        }
    }


    @Subscribe
    public void onBlockBreak(PlayerBreakBlockEvent event) {
        Log.INFO(event.getClass().getSimpleName());
        Player player = event.getUser();
        if (player.hasPermission("player.canBreak")) {
            PlayerUtils.sendInfo(player, "BlockBreak", event.getBlock().getType().getName());
        } else {
            event.setCancelled(true);
        }
    }
}
