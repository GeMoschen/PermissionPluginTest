package de.minestar.mscore;

import java.io.File;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.inject.Inject;

import de.gemo.permconfig.sponge.PermService;
import de.minestar.library.plugin.AbstractCore;
import de.minestar.library.utils.chat.PlayerUtils;


@Plugin(id = "MSLIB", name = "Minestar Library", version = "1.0")
public class LibraryCore extends AbstractCore {

    // ///////////////////////////////////////////////////////////////
    //
    // AbstractCore
    // The following lines should be in all plugins
    // NOTE: do NOT use the following events for out plugins:
    // - ConstructionEvent
    // - PreInitializationEvent
    // Using them could cause errors, when using our UtilClasses
    //
    // ///////////////////////////////////////////////////////////////

    @Inject
    PluginContainer pluginContainer;


    @Subscribe
    public void onPreInitialization(PreInitializationEvent event) {
        // startup
        if (!this.startUp(this.pluginContainer, event.getGame())) {
            // TODO: is there a way to disable plugins on the fly?
        }
    }


    @Subscribe
    @Override
    public void onServerStopping(ServerStoppingEvent event) {
        this.shutdown();
    }

    // ///////////////////////////////////////////////////////////////
    //
    // Events
    //
    // ///////////////////////////////////////////////////////////////

    private BlockListener blockListener;
    private PermService permissionService;


    @Override
    protected boolean commonEnable() {
        // initialize PlayerUtils
        PlayerUtils.initialize(this.getGame());

        try {
            this.permissionService = PermService.getInstance(false);
            this.getServiceManager().setProvider(this, PermissionService.class, this.permissionService);
            this.permissionService.init(this.getGame());
            this.permissionService.loadWorlds(new File(this.getDataFolder(), "Permissions/"));
        } catch (ProviderExistsException e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    protected boolean createListeners() {
        this.blockListener = new BlockListener();
        return true;
    }


    @Override
    protected boolean registerEvents(EventManager eventManager) {
        eventManager.register(this, this.blockListener);
        return true;
    }


    @Override
    protected boolean commonDisable() {
        this.permissionService.saveWorlds();
        return true;
    }

}