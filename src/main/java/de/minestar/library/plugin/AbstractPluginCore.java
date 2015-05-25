package de.minestar.library.plugin;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
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
import org.spongepowered.api.service.ServiceManager;


public abstract class AbstractPluginCore {

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

    private final Game _game;


    public AbstractPluginCore(final Game game) {
        this._game = game;
    }


    public final Game getGame() {
        return _game;
    }


    public final Server getServer() {
        return this.getGame().getServer();
    }


    public final ServiceManager getServiceManager() {
        return this.getGame().getServiceManager();
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onConstruction(ConstructionEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onPreInitialization(PreInitializationEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public final void onInitialization(InitializationEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onPostInitialization(PostInitializationEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onLoadComplete(LoadCompleteEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onServerStarting(ServerStartingEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onServerStarted(ServerStartedEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onServerStopping(ServerStoppingEvent event) {
    }


    /**
     * Needs @Subscribe
     * 
     * @param event
     */
    public void onServerStopped(ServerStoppedEvent event) {
    }
}
