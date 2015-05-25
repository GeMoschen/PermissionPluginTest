package de.minestar.mscore;

import static de.Log.INFO;

import java.io.File;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.ConstructionEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;
import org.spongepowered.api.service.permission.PermissionService;

import com.google.inject.Inject;

import de.gemo.permconfig.sponge.PermService;
import de.minestar.library.plugin.AbstractPluginCore;


@Plugin(id = "MinestarLibrary", name = "Minestar Library", version = "1.0")
public class LibraryCore extends AbstractPluginCore {

    private static LibraryCore INSTANCE;


    private static void setInstance(LibraryCore instance) {
        LibraryCore.INSTANCE = instance;
    }


    public static LibraryCore getInstance() {
        return LibraryCore.INSTANCE;
    }


    @Inject
    public LibraryCore(final Game game) {
        super(game);
    }


    @Subscribe
    @Override
    public void onConstruction(ConstructionEvent event) {
        INFO("ConstructionEvent");
        LibraryCore.setInstance(this);
    }


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