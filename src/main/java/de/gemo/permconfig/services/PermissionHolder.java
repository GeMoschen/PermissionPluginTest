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

package de.gemo.permconfig.services;

import java.io.File;
import java.util.Collection;

import org.spongepowered.api.service.permission.PermissionService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.gemo.permconfig.cache.Cache;
import de.gemo.permconfig.impl.DataHolder;
import de.gemo.permconfig.impl.Subject;
import de.gemo.permconfig.impl.WorldDataHolder;
import de.gemo.permconfig.utils.FileUtils;
import de.gemo.permconfig.utils.JsonUtils;

public class PermissionHolder {

	private final static File CONFIG_FOLDER = new File("config" + File.separator);
	private final static File CONFIG_FILE = new File(CONFIG_FOLDER, "config.json");
	private final static File WORLD_FOLDER = new File("worlds" + File.separator);
	private static volatile PermissionHolder INSTANCE;

	private final Cache cache;
	private final DataHolder dataHolder;
	public static PermissionService permissionService;

	// ///////////////////////////////////////////////////////////////
	//
	// STATIC METHODS
	//
	// ///////////////////////////////////////////////////////////////

	public static PermissionHolder getInstance() {
		PermissionHolder result = INSTANCE;
		if (result == null) {
			synchronized (PermissionHolder.class) {
				result = INSTANCE;
				if (result == null) {
					INSTANCE = result = new PermissionHolder();
				}
			}
		}
		return result;
	}

	// ///////////////////////////////////////////////////////////////
	//
	// PERMISSION-SERVICE
	//
	// ///////////////////////////////////////////////////////////////

	private PermissionHolder() {
		this.dataHolder = new DataHolder();
		this.cache = new Cache();
	}

	public boolean hasPermission(String worldName, String playerUUID, String permissionNode) {
		System.out.println("[CACHE] Checking '" + permissionNode + "' for '" + playerUUID + "' in '" + worldName + "'...");
		WorldDataHolder worldDataHolder = this.dataHolder.getWorldOrDefault(worldName);
		Subject player = worldDataHolder.getPlayer(playerUUID);
		return this.cache.hasPermission(worldDataHolder.getIdentifier(), player.getIdentifier(), permissionNode);
	}

	public boolean hasPermissionUncached(String worldName, String playerUUID, String permissionNode) {
		System.out.println("[NOCACHE] Checking '" + permissionNode + "' for '" + playerUUID + "' in '" + worldName + "'...");
		return this.dataHolder.getWorldOrDefault(worldName).getPlayer(playerUUID).hasPermission(permissionNode);
	}

	// ///////////////////////////////////////////////////////////////
	//
	// WORLD-DATA
	//
	// ///////////////////////////////////////////////////////////////

	public void loadWorlds() {
		loadWorlds(WORLD_FOLDER);
	}

	public void loadWorlds(File directory) {
		// we need a valid directory
		if (!directory.exists() || !directory.isDirectory()) {
			if (!directory.exists()) {
				directory.mkdir();
			}
			return;
		}

		try {
			File[] fileList = directory.listFiles();
			for (File file : fileList) {
				// ignore directories
				if (file.isDirectory() || !file.getName().endsWith(".json")) {
					continue;
				}

				String worldName = file.getName().replace(".json", "");
				System.out.println("[ INFO ] Loading world: " + worldName);
				WorldDataHolder worldDataHolder = WorldDataHolder.fromJson(worldName, FileUtils.readFile(file));
				if (worldDataHolder != null) {
					if (!dataHolder.hasWorld(worldName)) {
						System.out.println("[ INFO ] World '" + worldName + "' added!");
						dataHolder.addWorldDataHolder(worldDataHolder);
					} else {
						System.out.println("[ ERROR ] World '" + worldName + "' already exists!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean saveWorld(String worldName) {
		return saveWorld(worldName, WORLD_FOLDER);
	}

	public boolean saveWorld(String worldName, File directory) {
		// delete first
		if (directory.exists()) {
			directory.delete();
		}
		directory.mkdir();

		// get world
		WorldDataHolder world = this.dataHolder.getWorld(worldName);

		// we need a world
		if (world == null) {
			return false;
		}

		System.out.println("[ INFO ] Saving world '" + worldName + "'...");

		// save
		File worldFile = new File(directory, worldName + ".json");
		if (worldFile.exists()) {
			worldFile.delete();
		}

		// finally save
		return FileUtils.saveString(JsonUtils.prettyFormat(world.toJson()), worldFile);
	}

	public void saveWorlds() {
		Collection<WorldDataHolder> worlds = this.dataHolder.getAll();
		for (WorldDataHolder world : worlds) {
			this.saveWorld(world.getIdentifier());
		}
	}

	// ///////////////////////////////////////////////////////////////
	//
	// CONFIG
	//
	// ///////////////////////////////////////////////////////////////

	public void loadConfig() {
		// we need a valid directory
		if (!CONFIG_FOLDER.exists() || !CONFIG_FOLDER.isDirectory()) {
			System.out.println("[ INFO ] Config-File not found...");
			this.saveConfig();
			return;
		}

		try {
			// ignore directories
			if (!CONFIG_FILE.exists() || CONFIG_FILE.isDirectory()) {
				System.out.println("[ INFO ] Config-File not found...");
				this.saveConfig();
				return;
			}

			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(FileUtils.readFile(CONFIG_FILE)).getAsJsonObject();
			this.dataHolder.setDefaultWorldDataHolder(this.dataHolder.getWorldOrDefault(root.get("defaultWorld").getAsString()));
			System.out.println("DefaultWorldDataHolder set to '" + this.dataHolder.getDefaultWorldDataHolder().getIdentifier() + "'!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean saveConfig() {
		CONFIG_FOLDER.mkdir();

		// delete old file
		if (CONFIG_FILE.exists()) {
			CONFIG_FILE.delete();
		}

		JsonObject root = new JsonObject();
		root.addProperty("defaultWorld", this.dataHolder.getDefaultWorldDataHolder().getIdentifier());

		// finally save
		return FileUtils.saveString(JsonUtils.prettyFormat(root), CONFIG_FILE);
	}

	public DataHolder getDataHolder() {
		return this.dataHolder;
	}

}
