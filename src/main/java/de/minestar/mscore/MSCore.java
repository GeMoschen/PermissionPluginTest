package de.minestar.mscore;

import java.io.File;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ConstructionEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.LoadCompleteEvent;
import org.spongepowered.api.event.state.PostInitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerAboutToStartEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.inject.Inject;

import de.Log;
import de.gemo.permconfig.sponge.PermService;
import de.minestar.library.plugin.AbstractPluginCore;

@Plugin(id = "MSCore", name = "MS Core", version = "1.0")
public class MSCore extends AbstractPluginCore {

	@Inject
	Game game;

	private static MSCore INSTANCE;

	public static MSCore getInstance() {
		return MSCore.INSTANCE;
	}

	public static Game getGame() {
		return MSCore.INSTANCE.game;
	}

	public static Server getServer() {
		return MSCore.getGame().getServer();
	}

	public static ServiceManager getServiceManager() {
		return MSCore.getGame().getServiceManager();
	}

	@Subscribe
	@Override
	public void onConstruction(ConstructionEvent event) {
		Log.info("ConstructionEvent");
		MSCore.INSTANCE = this;
	}

	@Subscribe
	@Override
	public void onPreInitialization(PreInitializationEvent event) {
		Log.info("PreInitializationEvent");
		try {
			PermService permissionService = new PermService(true);
			MSCore.getServiceManager().setProvider(this, PermissionService.class, permissionService);
			permissionService.init(MSCore.getGame());
			permissionService.loadWorlds(new File("Permissions/"));
		} catch (ProviderExistsException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	@Override
	public void onInitialization(InitializationEvent event) {
		Log.info("InitializationEvent");
	}

	@Subscribe
	@Override
	public void onPostInitialization(PostInitializationEvent event) {
		Log.info("PostInitializationEvent");
	}

	@Override
	public void onLoadComplete(LoadCompleteEvent event) {
		Log.info("LoadCompleteEvent");
	}

	@Subscribe
	@Override
	public void onServerAboutToStart(ServerAboutToStartEvent event) {
		Log.info("ServerAboutToStartEvent");
	}

	@Subscribe
	@Override
	public void onServerStarting(ServerStartingEvent event) {
		Log.info("ServerStartingEvent");
	}

	@Subscribe
	@Override
	public void onServerStarted(ServerStartedEvent event) {
		Log.info("ServerStartedEvent");
		event.getGame().getEventManager().register(this, new BlockListener());
	}

	@Subscribe
	@Override
	public void onServerStopping(ServerStoppingEvent event) {
		Log.info("ServerStoppingEvent");
	}
	@Subscribe
	@Override
	public void onServerStopped(ServerStoppedEvent event) {
		Log.info("ServerStoppedEvent");
	}
}