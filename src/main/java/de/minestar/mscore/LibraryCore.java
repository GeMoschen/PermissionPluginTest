package de.minestar.mscore;

import static de.Log.*;

import java.io.*;

import org.spongepowered.api.event.*;
import org.spongepowered.api.event.state.*;
import org.spongepowered.api.plugin.*;
import org.spongepowered.api.service.*;
import org.spongepowered.api.service.permission.*;

import com.google.inject.*;

import de.gemo.permconfig.sponge.*;
import de.minestar.library.plugin.*;


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
        if (!this.startUp(this.pluginContainer, event.getGame(), new File("config/" + this.pluginContainer.getName() + "/"))) {
            // TODO: is there a way to disable plugins on the fly?
        }
    }


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