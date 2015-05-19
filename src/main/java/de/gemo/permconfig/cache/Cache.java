/*
 * Copyright (C) 2015 Kilian Gaertner
 * 
 * This file is part of PermissionSystem.
 * 
 * PermissionSystem is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * PermissionSystem is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PermissionSystem.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.gemo.permconfig.cache;

import de.gemo.permconfig.datastructure.StringHashMap;

public class Cache {

    public static final long CACHE_ALIVE_TIME = 15000; // 15 seconds

    private final StringHashMap<CachedWorld> map;

    public Cache() {
        this.map = new StringHashMap<CachedWorld>();
    }

    public boolean hasPermission(String worldName, String playerUUID, String permissionNode) {
        // get permission
        CachedWorld world = this.map.get(worldName);

        // permission found?
        if (world == null) {
            world = this.map.add(worldName, new CachedWorld(worldName));
        }

        // return result
        return world.hasPermission(playerUUID, permissionNode);
    }

}
