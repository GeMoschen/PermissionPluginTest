package de.minestar.library.utils.permissions;

import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.common.base.Optional;

import de.minestar.library.utils.permissions.units.Group;
import de.minestar.mscore.MSCore;

public class PermissionUtils {

	private static PermissionService permissionService;

	static {
		Optional<PermissionService> service = MSCore.getGame().getServiceManager().provide(PermissionService.class);
		if (service.isPresent()) {
			PermissionUtils.permissionService = service.get();
		} else {
			PermissionUtils.permissionService = null;
		}

		PermissionUtils.playerMap = new HashMap<UUID, Group>();
	}

	public static PermissionService getPermissionService() {
		return permissionService;
	}
	private static HashMap<UUID, Group> playerMap;

	public static boolean addPlayer(UUID uuid, Group group) {
		return playerMap.put(uuid, group) == null;
	}

	public static boolean removePlayer(UUID uuid) {
		return playerMap.remove(uuid) != null;
	}

	public static boolean hasPlayer(UUID uuid) {
		return playerMap.containsKey(uuid);
	}

	public static boolean hasPermission(Player player, String permissionNode) {
		if (playerMap.containsKey(player.getUniqueId())) {
			return playerMap.get(player.getUniqueId()).hasPermission(permissionNode);
		}
		return player.hasPermission(permissionNode);
	}
}
