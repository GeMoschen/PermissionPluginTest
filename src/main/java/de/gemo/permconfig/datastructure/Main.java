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

import de.gemo.permconfig.services.PermissionHolder;

public class Main {

    public static void main(String[] args) {
        PermissionHolder service = PermissionHolder.getInstance();
//        service.createTestData();
        service.loadWorlds();
        service.loadConfig();

        System.out.println("");
        testNode(service, "payPlayer", "pay.add");
        testNode(service, "payPlayer", "free.add");
        testNode(service, "freePlayer", "pay.add");
        testNode(service, "freePlayer", "pay.remove");
    }

    private static void testNode(PermissionHolder service, String playerName, String node) {
        System.out.println("Checking '" + node + "' for player '" + playerName + "' = " + service.hasPermission("world", playerName, node));
    }

}
