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
import de.gemo.permconfig.services.PermissionHolder;

public class CachedSubject {
    private final String worldName, playerUUID;
    private final StringHashMap<CachedPermission> map;

    public CachedSubject(String worldName, String playerUUID) {
        this.worldName = worldName;
        this.playerUUID = playerUUID;
        this.map = new StringHashMap<CachedPermission>();
    }

    public boolean hasPermission(String permissionNode) {
        // get permission
        CachedPermission permission = this.map.get(permissionNode);

        // permission found?
        if (permission == null) {
            permission = this.map.add(permissionNode, new CachedPermission(permissionNode, PermissionHolder.getInstance().hasPermissionUncached(this.worldName, this.playerUUID, permissionNode)));
        }

        // check if the cached permission expired
        if (permission.expired()) {
            permission.setResult(PermissionHolder.getInstance().hasPermissionUncached(this.worldName, this.playerUUID, permissionNode));
        }

        // return result
        return permission.getResult();
    }
}
