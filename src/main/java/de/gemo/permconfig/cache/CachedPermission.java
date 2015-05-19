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

public class CachedPermission {

    private final String permissionNode;
    private boolean result;
    private long timestamp;

    public CachedPermission(String permissionNode, boolean result) {
        this.permissionNode = permissionNode;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPermissionNode() {
        return permissionNode;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
        this.updateTimestamp(System.currentTimeMillis() + Cache.CACHE_ALIVE_TIME);
    }

    public boolean expired() {
        return System.currentTimeMillis() > this.timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    private void updateTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
