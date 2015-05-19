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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.gemo.permconfig.datastructure.Manager;
import de.gemo.permconfig.interfaces.Identifiable;
import de.gemo.permconfig.utils.JsonUtils;

public class WorldDataHolder implements Identifiable {

	private static final SubjectPlayer EMPTY_SUBJECT = SubjectPlayer.EMPTY_SUBJECT;

	private final String name;
	private final Manager<Subject> groups;
	private final Manager<SubjectPlayer> players;

	public WorldDataHolder(String name) {
		this.name = name;
		this.groups = new Manager<Subject>();
		this.players = new Manager<SubjectPlayer>();
	}

	public String getIdentifier() {
		return this.name;
	}

	private static class WrappedSubject {
		private Subject subject;
		private String parent = null;

		protected WrappedSubject(Subject subject) {
			this.subject = subject;
		}
	}

	@Override
	public String toString() {
		return this.getIdentifier();
	}

	// ///////////////////////////////////////////////////////////////
	//
	// JSON
	//
	// ///////////////////////////////////////////////////////////////

	public static WorldDataHolder fromJson(String name, String jsonString) {
		WorldDataHolder dataHolder = new WorldDataHolder(name);

		try {
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(jsonString).getAsJsonObject();

			// GROUPS
			JsonObject groupsObject = root.get(JsonUtils.JSON_GROUPS).getAsJsonObject();
			Set<Entry<String, JsonElement>> groupEntrySet = groupsObject.entrySet();
			List<WrappedSubject> subjectList = new ArrayList<WrappedSubject>();
			for (Entry<String, JsonElement> entry : groupEntrySet) {

				Subject group = dataHolder.addGroup(entry.getKey().replaceAll("([^a-zA-Z0-9-_])", ""));
				WrappedSubject wrappedSubject = new WrappedSubject(group);
				subjectList.add(wrappedSubject);

				JsonObject groupObject = entry.getValue().getAsJsonObject();
				if (groupObject.has(JsonUtils.JSON_PARENT)) {
					wrappedSubject.parent = groupObject.get(JsonUtils.JSON_PARENT).getAsString().replaceAll("([^a-zA-Z0-9-_])", "");
				}

				if (groupObject.has(JsonUtils.JSON_WHITELIST)) {
					JsonArray permissionsArray = groupObject.get(JsonUtils.JSON_WHITELIST).getAsJsonArray();
					for (int index = 0; index < permissionsArray.size(); index++) {
						group.grantPermission(permissionsArray.get(index).getAsString().replaceAll("([^a-zA-Z0-9.])", ""));
					}
				}

				if (groupObject.has(JsonUtils.JSON_BLACKLIST)) {
					JsonArray permissionsArray = groupObject.get(JsonUtils.JSON_BLACKLIST).getAsJsonArray();
					for (int index = 0; index < permissionsArray.size(); index++) {
						group.blacklistPermission(permissionsArray.get(index).getAsString().replaceAll("([^a-zA-Z0-9.])", ""));
					}
				}
			}

			// setup parents
			for (WrappedSubject wrappedSubject : subjectList) {
				if (wrappedSubject.parent != null) {
					if (dataHolder.getGroup(wrappedSubject.parent) != null) {
						wrappedSubject.subject.setParent(dataHolder.getGroup(wrappedSubject.parent));
					}
				}
			}

			// PLAYERS
			JsonObject playersObject = root.get(JsonUtils.JSON_PLAYERS).getAsJsonObject();
			Set<Entry<String, JsonElement>> playerEntrySet = playersObject.entrySet();
			subjectList.clear();
			for (Entry<String, JsonElement> entry : playerEntrySet) {
				JsonObject playerObject = entry.getValue().getAsJsonObject();

				SubjectPlayer player = dataHolder.addPlayer(entry.getKey());
				player.setParent(dataHolder.getGroup(playerObject.get(JsonUtils.JSON_PARENT).getAsString().replaceAll("([^a-zA-Z0-9-_])", "")));

				if (playerObject.has(JsonUtils.JSON_WHITELIST)) {
					JsonArray permissionsArray = playerObject.get(JsonUtils.JSON_WHITELIST).getAsJsonArray();
					for (int i = 0; i < permissionsArray.size(); i++) {
						player.grantPermission(ContextHolder.getOrCreate(name), permissionsArray.get(i).getAsString().replaceAll("([^a-zA-Z0-9.])", ""));
					}
				}

				if (playerObject.has(JsonUtils.JSON_BLACKLIST)) {
					JsonArray permissionsArray = playerObject.get(JsonUtils.JSON_BLACKLIST).getAsJsonArray();
					for (int i = 0; i < permissionsArray.size(); i++) {
						player.blacklistPermission(permissionsArray.get(i).getAsString().replaceAll("([^a-zA-Z0-9.])", ""));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dataHolder;
	}

	public JsonObject toJson() {
		JsonObject root = new JsonObject();

		// GROUPS
		JsonObject groupsObject = new JsonObject();
		Collection<Subject> groupList = this.getGroups();
		for (Subject group : groupList) {
			groupsObject.add(group.getIdentifier(), group.toJson());
		}
		root.add(JsonUtils.JSON_GROUPS, groupsObject);

		// PLAYERS
		JsonObject playersObject = new JsonObject();
		Collection<SubjectPlayer> playerList = this.getPlayers();
		for (Subject player : playerList) {
			playersObject.add(player.getIdentifier(), player.toJson());
		}
		root.add(JsonUtils.JSON_PLAYERS, playersObject);

		return root;
	}

	// ///////////////////////////////////////////////////////////////
	//
	// GROUPS
	//
	// ///////////////////////////////////////////////////////////////

	public boolean hasGroup(String groupName) {
		return this.groups.has(groupName);
	}

	public Subject getGroup(String groupName) {
		return this.groups.get(groupName);
	}

	public Subject addGroup(String groupName) {
		return this.addGroup(new Subject(groupName));
	}

	public Subject addGroup(Subject group) {
		if (this.hasGroup(group.getIdentifier())) {
			return this.getGroup(group.getIdentifier());
		}
		this.groups.add(group);
		return group;
	}

	public Collection<Subject> getGroups() {
		return this.groups.getAll();
	}

	// ///////////////////////////////////////////////////////////////
	//
	// PLAYERS
	//
	// ///////////////////////////////////////////////////////////////

	public boolean hasPlayer(String playerUUID) {
		return this.players.has(playerUUID);
	}

	public SubjectPlayer getPlayer(String playerUUID) {
		SubjectPlayer player = this.players.get(playerUUID);
		return (player == null ? EMPTY_SUBJECT : player);
	}

	public SubjectPlayer addPlayer(String playerUUID) {
		return this.addPlayer(new SubjectPlayer(playerUUID));
	}

	public SubjectPlayer addPlayer(SubjectPlayer player) {
		if (this.hasPlayer(player.getIdentifier())) {
			return this.getPlayer(player.getIdentifier());
		}
		this.players.add(player);
		return player;
	}

	public Collection<SubjectPlayer> getPlayers() {
		return this.players.getAll();
	}

}
