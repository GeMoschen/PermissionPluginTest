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

public class CachedWorld {
    private final String worldName;
    private final StringHashMap<CachedSubject> map;

    public CachedWorld(String worldName) {
        this.worldName = worldName;
        this.map = new StringHashMap<CachedSubject>();
    }

    public boolean hasPermission(String playerUUID, String permissionNode) {
        // get permission
        CachedSubject subject = this.map.get(playerUUID);

        // permission found?
        if (subject == null) {
            subject = this.map.add(playerUUID, new CachedSubject(this.worldName, playerUUID));
        }

        // return result
        return subject.hasPermission(permissionNode);
    }

}
