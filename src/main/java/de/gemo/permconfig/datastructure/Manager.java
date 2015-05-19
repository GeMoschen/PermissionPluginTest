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

package de.gemo.permconfig.datastructure;

import de.gemo.permconfig.interfaces.IManager;
import de.gemo.permconfig.interfaces.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Manager<T extends Identifiable> implements IManager<T> {
    private final Map<String, T> identifiables;

    public Manager() {
        this.identifiables = Collections.synchronizedMap(new HashMap<String, T>());
    }

    public boolean has(String identifier) {
        return this.identifiables.containsKey(identifier);
    }

    public T get(String identifier) {
        return this.identifiables.get(identifier);
    }

    public T remove(String identifier) {
        return this.identifiables.remove(identifier);
    }

    public boolean add(T identifiable) {
        if (this.has(identifiable.getIdentifier())) {
            return false;
        }
        this.identifiables.put(identifiable.getIdentifier(), identifiable);
        return true;
    }

    public Collection<T> getAll() {
        return Collections.unmodifiableCollection(this.identifiables.values());
    }

    public void clear() {
        this.identifiables.clear();
    }

}
