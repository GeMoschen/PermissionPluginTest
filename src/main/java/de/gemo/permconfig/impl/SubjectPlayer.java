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

import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.context.Context;
import org.spongepowered.api.util.Tristate;

import de.gemo.permconfig.services.PermissionHolder;

public class SubjectPlayer extends Subject {

	public static final SubjectPlayer EMPTY_SUBJECT = new EmptySubjectPlayer();

	public SubjectPlayer(String name) {
		super(name);
	}

	public SubjectPlayer(String name, Subject parent) {
		super(name, parent);
	}

	public void grantPermission(Context context, String permissionNode) {
		super.grantPermission(permissionNode);
		if (PermissionHolder.permissionService != null) {
			if (!this.blackList.hasNode(permissionNode)) {
				System.out.println(PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()));
				boolean result = PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permissionNode, Tristate.TRUE);
				System.out.println("set '" + permissionNode + "': " + result);
			}
		}
	}

	public void revokePermission(Context context, String permissionNode) {
		super.revokePermission(permissionNode);
		if (PermissionHolder.permissionService != null) {
			if (!this.blackList.hasNode(permissionNode)) {
				PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(Collections.EMPTY_SET, permissionNode, Tristate.UNDEFINED);
			} else {
				PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(Collections.EMPTY_SET, permissionNode, Tristate.FALSE);
			}
		}
	}

	public void blacklistPermission(Context context, String permissionNode) {
		super.blacklistPermission(permissionNode);
		if (PermissionHolder.permissionService != null) {
			PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(Collections.EMPTY_SET, permissionNode, Tristate.FALSE);
		}
	}

	public void revokeBlacklistetPermission(Context context, String permissionNode) {
		super.revokeBlacklistedPermission(permissionNode);
		if (PermissionHolder.permissionService != null) {
			if (!this.whiteList.hasNode(permissionNode)) {
				PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(Collections.EMPTY_SET, permissionNode, Tristate.UNDEFINED);
			} else {
				PermissionHolder.permissionService.getUserSubjects().get(this.getIdentifier()).getSubjectData().setPermission(Collections.EMPTY_SET, permissionNode, Tristate.TRUE);
			}
		}
	}

	private static class EmptySubjectPlayer extends SubjectPlayer {
		private EmptySubjectPlayer() {
			super("EMPTY_SUBJECTPLAYER");
		}

		@Override
		public boolean hasPermission(String permissionNode) {
			return false;
		}

		@Override
		public void grantPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void grantPermission(Context context, String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokePermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokePermission(Context context, String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void blacklistPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void blacklistPermission(Context context, String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokeBlacklistedPermission(String permissionNode) {
			// NOTHING TO DO HERE
		}

		@Override
		public void revokeBlacklistetPermission(Context context, String permissionNode) {
			// NOTHING TO DO HERE
		}
	}

}
