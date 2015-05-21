package de.minestar.mscore;

import org.slf4j.Logger;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerPlaceBlockEvent;
import org.spongepowered.api.service.permission.SubjectData;

import de.gemo.permconfig.services.PermissionHolder;
import de.minestar.library.utils.chat.PlayerUtils;

public class BlockListener {

	private Logger logger;

	public BlockListener() {
		this.logger = MSCore.getInstance().logger;
	}

	@Subscribe
	public void onBlockPlace(PlayerPlaceBlockEvent event) {
		logger.info(event.getClass().getSimpleName());

		Player player = event.getUser();

		System.out.println(player.getUniqueId());

		System.out.println(PermissionHolder.permissionService.getUserSubjects().get(player.getIdentifier()));
		System.out.println("player.getIdentifier() : " + player.getIdentifier());
		System.out.println("wildcard.*: " + player.hasPermission("wildcard.doit"));
		System.out.println("player.canPlace: " + player.hasPermission("player.canPlace"));
		System.out.println("player.canDestroy: " + player.hasPermission("player.canDestroy"));
		if (player.hasPermission(SubjectData.GLOBAL_CONTEXT, "player.canPlace")) {
			PlayerUtils.sendInfo(player, "BlockPlace OLD", event.getReplacementBlock().getState().getType().getName());
			PlayerUtils.sendInfo(player, "BlockPlace NEW", event.getBlock().getType().getName());
		} else {
			event.setCancelled(true);
		}
	}
	@Subscribe
	public void onBlockBreak(PlayerBreakBlockEvent event) {
		logger.info(event.getClass().getSimpleName());
		Player player = event.getUser();
		if (player.hasPermission("player.canBreak")) {
			PlayerUtils.sendInfo(player, "BlockBreak", event.getBlock().getType().getName());
		} else {
			event.setCancelled(true);
		}
	}
}
