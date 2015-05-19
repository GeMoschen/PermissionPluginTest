/*
 * Copyright (C) 2013 Kilian Gaertner
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

package de.minestar.library.utils.permissions.units;

import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

import de.minestar.library.utils.permissions.PermissionUtils;

public class Group implements Comparable<Group> {

	private final String name;
	private final String world;
	private final int hashCode;

	private Group parentGroup;
	private PermissionTree permissions;

	public Group(String name, String world, PermissionTree permissions, Group parentGroup) {
		this.name = name;
		this.world = world;
		this.permissions = permissions;
		this.parentGroup = parentGroup;
		this.hashCode = this.name.hashCode();
	}

	public Group(String name, String world, PermissionTree permissions) {
		this(name, world, new PermissionTree(), null);
	}

	public Group(String name, String world) {
		this(name, world, new PermissionTree());
	}

	public Group(String name, String world, Group parentGroup) {
		this(name, world, new PermissionTree(), parentGroup);
	}

	public String getName() {
		return this.name;
	}

	public boolean hasPermission(String permissionNode) {
		return this.permissions.hasNode(permissionNode) || ((this.parentGroup != null) ? this.parentGroup.hasPermission(permissionNode) : false);
	}

	public void grantPermission(String permissionNode) {
		this.permissions.addNode(permissionNode);
		PermissionUtils.getPermissionService().getDefaultData().setPermission(SubjectData.GLOBAL_CONTEXT, "group." + this.name + "." + permissionNode, Tristate.TRUE);
	}

	public void revokePermission(String permissionNode) {
		this.permissions.removeNode(permissionNode);
		PermissionUtils.getPermissionService().getDefaultData().setPermission(SubjectData.GLOBAL_CONTEXT, "group." + this.name + "." + permissionNode, Tristate.FALSE);
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	public int compareTo(Group other) {
		return other.hashCode() - this.hashCode();
	}

}
