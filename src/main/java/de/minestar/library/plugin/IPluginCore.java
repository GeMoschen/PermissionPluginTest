package de.minestar.library.plugin;

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

public interface IPluginCore {

	// SPONGE calls the events in the following order:

	// 01. onConstruction
	// 02. onPreInitialization
	// 03. onInitialization
	// 04. onPostInitialization
	// 05. onLoadComplete
	// 06. onServerAboutToStart
	// 07. onServerStarting
	// 08. onServerStarted
	// 09. onServerStopping
	// 10. onServerStopped
	//
	// The PluginCore MUST use @Subscribe for all used events.

	// FOR MORE INFORMATION, see:
	// https://docs.spongepowered.org/en/latest/plugins/quick-start/
	// https://docs.spongepowered.org/en/latest/plugins/plugin-lifecycle/

	public void onConstruction(ConstructionEvent event);

	public void onPreInitialization(PreInitializationEvent event);

	public void onInitialization(InitializationEvent event);

	public void onPostInitialization(PostInitializationEvent event);

	public void onLoadComplete(LoadCompleteEvent event);

	public void onServerAboutToStart(ServerAboutToStartEvent event);

	public void onServerStarting(ServerStartingEvent event);

	public void onServerStarted(ServerStartedEvent event);

	public void onServerStopping(ServerStoppingEvent event);

	public void onServerStopped(ServerStoppedEvent event);
}
