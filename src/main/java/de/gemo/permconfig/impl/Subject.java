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

package de.gemo.permconfig.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import de.gemo.permconfig.datastructure.PermissionTree;
import de.gemo.permconfig.interfaces.Identifiable;
import de.gemo.permconfig.interfaces.Permissionable;
import de.gemo.permconfig.services.PermissionHolder;
import de.gemo.permconfig.utils.JsonUtils;

public class Subject implements Identifiable, Permissionable {

	public static final Subject EMPTY_SUBJECT = new EmptySubject();

	private final String identifier;
	private Subject parent;
	private Map<String, Subject> children;
	protected PermissionTree whiteList, blackList;

	public Subject(String name) {
		this.identifier = name;
		this.whiteList = new PermissionTree();
		this.blackList = new PermissionTree();
		this.children = Collections.synchronizedMap(new HashMap<String, Subject>());
		this.parent = null;
	}

	public Subject(String name, Subject parent) {
		this(name);
		this.setParent(parent);
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	public Subject getParent() {
		return this.parent;
	}

	public void setParent(Subject parent) {
		if (this.parent != parent && this.parent != null) {
			this.parent.children.remove(this.getIdentifier());
		}
		this.parent = parent;
		this.parent.children.put(this.getIdentifier(), this);
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean hasPermission(String permissionNode) {
		System.out.println("permissionService found: " + (PermissionHolder.permissionService != null));
		return (!this.blackList.hasNode(permissionNode) && this.whiteList.hasNode(permissionNode)) || ((this.hasParent() ? this.getParent().hasPermission(permissionNode) : false) && !this.blackList.hasNode(permissionNode));
	}

	public void grantPermission(String permissionNode) {
		this.whiteList.addNode(permissionNode);
	}

	public void revokePermission(String permissionNode) {
		this.whiteList.removeNode(permissionNode);
	}

	public void blacklistPermission(String permissionNode) {
		this.blackList.addNode(permissionNode);
	}

	public void revokeBlacklistedPermission(String permissionNode) {
		this.blackList.removeNode(permissionNode);
	}

	public List<String> getWhitelistNodes() {
		return this.whiteList.toList();
	}

	public List<String> getBlacklistNodes() {
		return this.blackList.toList();
	}

	public Collection<Subject> getChildren() {
		return Collections.unmodifiableCollection(this.children.values());
	}

	public boolean hasChildren() {
		return !this.children.isEmpty();
	}

	public JsonObject toJson() {
		JsonObject root = new JsonObject();
		if (this.parent != null) {
			root.addProperty(JsonUtils.JSON_PARENT, this.parent.getIdentifier());
		}

		// whitelist
		List<String> nodeList = this.getWhitelistNodes();
		if (!nodeList.isEmpty()) {
			JsonArray array = new JsonArray();
			for (String node : nodeList) {
				array.add(new JsonPrimitive(node));
			}
			root.add(JsonUtils.JSON_WHITELIST, array);
		}

		// blacklist
		nodeList = this.getBlacklistNodes();
		if (!nodeList.isEmpty()) {
			JsonArray array = new JsonArray();
			for (String node : nodeList) {
				array.add(new JsonPrimitive(node));
			}
			root.add(JsonUtils.JSON_BLACKLIST, array);
		}
		return root;
	}

	@Override
	public String toString() {
		return this.identifier;
	}

	private static class EmptySubject extends Subject {
		private EmptySubject() {
			super("EMPTY_SUBJECT");
		}

		@Override
		public boolean hasPermission(String permissionNode) {
			return false;
		}

		@Override
		public void setParent(Subject parent) {
			// NOTHING TO DO HERE
		}

		@Override
		public void grantPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokePermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void blacklistPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokeBlacklistedPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}
	}
}
