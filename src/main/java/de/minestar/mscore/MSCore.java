package de.minestar.mscore;

import java.io.File;

import org.slf4j.Logger;
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

import de.gemo.permconfig.services.PermissionHolder;
import de.gemo.permconfig.sponge.PermService;
import de.minestar.library.plugin.AbstractPluginCore;

@Plugin(id = "MSCore", name = "MS Core", version = "1.0")
public class MSCore extends AbstractPluginCore {

	@Inject
	Logger logger;

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

	public Logger getLogger() {
		return logger;
	}

	@Subscribe
	@Override
	public void onConstruction(ConstructionEvent event) {
		logger.info("ConstructionEvent");
		MSCore.INSTANCE = this;
	}

	@Subscribe
	@Override
	public void onPreInitialization(PreInitializationEvent event) {
		logger.info("PreInitializationEvent");
		try {
			PermService permissionService = new PermService();
			MSCore.getServiceManager().setProvider(this, PermissionService.class, permissionService);
			permissionService.init(MSCore.getGame());
		} catch (ProviderExistsException e) {
			e.printStackTrace();
		}
	}

	@Subscribe
	@Override
	public void onInitialization(InitializationEvent event) {
		logger.info("InitializationEvent");
	}

	@Subscribe
	@Override
	public void onPostInitialization(PostInitializationEvent event) {
		logger.info("PostInitializationEvent");
	}

	@Override
	public void onLoadComplete(LoadCompleteEvent event) {
		logger.info("LoadCompleteEvent");
	}

	@Subscribe
	@Override
	public void onServerAboutToStart(ServerAboutToStartEvent event) {
		logger.info("ServerAboutToStartEvent");
	}

	@Subscribe
	@Override
	public void onServerStarting(ServerStartingEvent event) {
		logger.info("ServerStartingEvent");
	}

	@Subscribe
	@Override
	public void onServerStarted(ServerStartedEvent event) {
		logger.info("ServerStartedEvent");
		PermissionHolder.permissionService = getGame().getServiceManager().provideUnchecked(PermissionService.class);
		PermissionHolder.getInstance().loadWorlds(new File("Permissions/"));
		event.getGame().getEventManager().register(this, new BlockListener());
	}

	@Subscribe
	@Override
	public void onServerStopping(ServerStoppingEvent event) {
		logger.info("ServerStoppingEvent");
	}
	@Subscribe
	@Override
	public void onServerStopped(ServerStoppedEvent event) {
		logger.info("ServerStoppedEvent");
	}
}