package de.minestar.mscore;

import static de.Log.INFO;

import java.io.File;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ConstructionEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.inject.Inject;

import de.gemo.permconfig.sponge.PermService;
import de.minestar.library.plugin.AbstractCore;


@Plugin(id = "MinestarLibrary", name = "Minestar Library", version = "1.0")
public class LibraryCore extends AbstractCore {

    // ///////////////////////////////////////////////////////////////
    //
    // Static methods
    //
    // ///////////////////////////////////////////////////////////////

    private static LibraryCore INSTANCE;


    private static void setInstance(LibraryCore instance) {
        LibraryCore.INSTANCE = instance;
    }


    public static LibraryCore getInstance() {
        return LibraryCore.INSTANCE;
    }

    // ///////////////////////////////////////////////////////////////
    //
    // AbstractPluginCore
    // The following lines should be contained in all plugins extending the AbstractCore
    //
    // ///////////////////////////////////////////////////////////////

    @Inject
    PluginContainer pluginContainer;


    @Subscribe
    @Override
    public void onConstruction(ConstructionEvent event) {
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
    // Enable
    //
    // ///////////////////////////////////////////////////////////////

    @Override
    protected boolean commonEnable() {
        LibraryCore.setInstance(this);
        return true;
    }


    // ///////////////////////////////////////////////////////////////
    //
    // Events
    //
    // ///////////////////////////////////////////////////////////////

    @Subscribe
    @Override
    public void onPreInitialization(PreInitializationEvent event) {
        INFO("PreInitializationEvent");
        try {
            PermService permissionService = PermService.getInstance(true);
            this.getServiceManager().setProvider(this, PermissionService.class, permissionService);
            permissionService.init(this.getGame());
            permissionService.loadWorlds(new File("Permissions/"));
        } catch (ProviderExistsException e) {
            e.printStackTrace();
        }
    }

}