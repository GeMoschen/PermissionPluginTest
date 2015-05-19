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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StringHashMap<T> {
    private final Map<String, T> map;

    public StringHashMap() {
        this.map = Collections.synchronizedMap(new HashMap<String, T>());
    }

    public boolean equals(String key, T element) {
        final T value = this.get(key);
        if (value != null) {
            return value.equals(element);
        }
        return false;
    }

    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    public T get(String key) {
        return this.map.get(key);
    }

    public T remove(String key) {
        return this.map.remove(key);
    }

    public T getOrAdd(String key, T value) {
        T tempValue = this.get(key);
        if (tempValue != null) {
            return tempValue;
        }
        return this.add(key, value);
    }

    public T add(String key, T value) {
        this.map.put(key, value);
        return value;
    }

    public T addIfAbsent(String key, T value) {
        T tempValue = this.get(key);
        if (tempValue != null) {
            return tempValue;
        }
        tempValue = value;
        return tempValue;
    }

}
