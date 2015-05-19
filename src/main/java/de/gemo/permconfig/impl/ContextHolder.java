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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.service.permission.context.Context;

import de.minestar.mscore.MSCore;

public class ContextHolder {
	private static Map<String, Context> contexts = Collections.synchronizedMap(new HashMap<String, Context>());

	public static Context getOrCreate(String worldName) {
		Context context = contexts.get(worldName);
		if (context == null) {
			context = MSCore.getServer().getWorld(worldName).get().getContext();
			System.out.println(context.getKey() + " - " + context.getValue() + " - " + context.getValue());
			contexts.put(worldName, context);
		}
		return context;
	}
}
