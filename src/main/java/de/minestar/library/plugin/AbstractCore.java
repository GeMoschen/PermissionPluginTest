package de.minestar.library.plugin;

import java.io.*;

import org.spongepowered.api.*;
import org.spongepowered.api.event.state.*;
import org.spongepowered.api.plugin.*;
import org.spongepowered.api.service.*;
import org.spongepowered.api.service.command.*;
import org.spongepowered.api.service.event.*;
import org.spongepowered.api.service.scheduler.*;

import com.google.common.base.*;

import de.minestar.library.utils.log.*;


public abstract class AbstractCore {

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

    private PluginContainer _pluginContainer;
    private Game _game;
    private File _dataFolder;


    public final String getName() {
        return this._pluginContainer.getName();
    }


    public final String getVersion() {
        return this._pluginContainer.getVersion();
    }


    public final String getId() {
        return this._pluginContainer.getId();
    }


    public final Game getGame() {
        return this._game;
    }


    public final Server getServer() {
        return this.getGame().getServer();
    }


    public final File getDataFolder() {
        return this._dataFolder;
    }


    public final ServiceManager getServiceManager() {
        return this.getGame().getServiceManager();
    }


    public final boolean startUp(PluginContainer pluginContainer, Game game) {
        // check preconditions
        Preconditions.checkNotNull(pluginContainer);
        Preconditions.checkNotNull(game);

        // set vars
        this._pluginContainer = pluginContainer;
        this._game = game;
        this._dataFolder = new File("config/plugins/" + this.getId() + "/");

        // initialize
        return this.initialize();
    }


    public final boolean shutdown() {
        // disable common things
        if (!this.commonDisable()) {
            LogUtils.ERROR(this, "Can't disable common things! Possible data loss!");
        }

        LogUtils.INFO(this, "Disabled v" + this.getVersion() + "!");
        return true;
    }


    protected final boolean initialize() {
        // make dir
        this.getDataFolder().mkdirs();

        // load configs
        if (!this.loadConfigurations()) {
            LogUtils.ERROR(this, "Can't load configurations! Plugin is not enabled!");
            return false;
        }

        // create managers
        if (!this.createManagers()) {
            LogUtils.ERROR(this, "Can't create managers! Plugin is not enabled!");
            return false;
        }

        // create listeners
        if (!this.createListeners()) {
            LogUtils.ERROR(this, "Can't create listeners! Plugin is not enabled!");
            return false;
        }

        // register commands
        if (!this.registerCommands(this._game.getCommandDispatcher())) {
            LogUtils.ERROR(this, "Can't register commands! Plugin is not enabled!");
            return false;
        }

        // register events
        if (!this.registerEvents(this._game.getEventManager())) {
            LogUtils.ERROR(this, "Can't register events! Plugin is not enabled!");
            return false;
        }

        // create threads
        if (!this.createThreads()) {
            LogUtils.ERROR(this, "Can't create threads! Plugin is not enabled!");
            return false;
        }

        // start asynchronous threads
        if (!this.startAsynchronousThreads(this._game.getAsyncScheduler())) {
            LogUtils.ERROR(this, "Can't start asynchronous threads! Plugin is not enabled!");
            return false;
        }

        // start synchronous threads
        if (!this.startSynchronousThreads(this._game.getSyncScheduler())) {
            LogUtils.ERROR(this, "Can't start synchronous threads! Plugin is not enabled!");
            return false;
        }

        // enable common things
        if (!this.commonEnable()) {
            LogUtils.ERROR(this, "Can't enable common things! Plugin is not enabled!");
            return false;
        }

        // print info & return
        LogUtils.INFO(this, "Enabled v" + this.getVersion() + "!");
        return true;
    }


    protected boolean loadConfigurations() {
        return true;
    }


    protected boolean createManagers() {
        return true;
    }


    protected boolean createListeners() {
        return true;
    }


    protected boolean registerCommands(CommandService commandService) {
        return true;
    }


    protected boolean registerEvents(EventManager eventManager) {
        return true;
    }


    protected boolean createThreads() {
        return true;
    }


    protected boolean startAsynchronousThreads(AsynchronousScheduler scheduler) {
        return true;
    }


    protected boolean startSynchronousThreads(SynchronousScheduler scheduler) {
        return true;
    }


    protected boolean commonEnable() {
        return true;
    }


    protected boolean commonDisable() {
        return true;
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
    public void onInitialization(InitializationEvent event) {
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
