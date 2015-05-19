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

package de.gemo.permconfig.impl;

import java.util.Collection;

import de.gemo.permconfig.datastructure.Manager;

public class DataHolder {

    private final Manager<WorldDataHolder> worlds;
    private WorldDataHolder defaultDataHolder;

    public DataHolder() {
        this.worlds = new Manager<WorldDataHolder>();
        this.defaultDataHolder = new WorldDataHolder("DEFAULT");
    }

    public void setDefaultWorldDataHolder(WorldDataHolder dataHolder) {
        this.defaultDataHolder = dataHolder;
    }

    public WorldDataHolder getDefaultWorldDataHolder() {
        return defaultDataHolder;
    }

    public WorldDataHolder getWorld(String worldName) {
        return this.worlds.get(worldName);
    }

    public WorldDataHolder getWorldOrDefault(String worldName) {
        if (!this.hasWorld(worldName)) {
            return this.defaultDataHolder;
        }
        return this.getWorld(worldName);
    }

    public boolean hasWorld(String worldName) {
        return this.worlds.has(worldName);
    }

    public WorldDataHolder addWorldDataHolder(WorldDataHolder worldDataHolder) {
        if (this.hasWorld(worldDataHolder.getIdentifier())) {
            return this.getWorld(worldDataHolder.getIdentifier());
        }

        this.worlds.add(worldDataHolder);
        return worldDataHolder;
    }

    public WorldDataHolder addWorld(String worldName) {
        if (this.hasWorld(worldName)) {
            return this.getWorld(worldName);
        }

        WorldDataHolder dataHolder = new WorldDataHolder(worldName);
        this.worlds.add(dataHolder);
        return dataHolder;
    }

    public Collection<WorldDataHolder> getAll() {
        return this.worlds.getAll();
    }

    public void clear() {
        this.worlds.clear();
        this.defaultDataHolder = new WorldDataHolder("DEFAULT");
    }
}
