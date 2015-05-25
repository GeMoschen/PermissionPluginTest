package de.minestar.library.command;

import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;


public abstract class Command implements CommandCallable {

    private final String _name;
    private final String _permissionNode;
    private Optional<? extends Text> _shortDescription, _helpText;
    private Text _usageText;


    public Command(final String name, final String permissionNode) {
        this._name = name;
        this._permissionNode = permissionNode;
        this.setShortDescription("");
        this.setHelpText("");
        this.setUsageText("");
    }


    public final String getName() {
        return this._name;
    }


    public final String getPermissionNode() {
        return this._permissionNode;
    }


    public final Command setShortDescription(final String shortDescription) {
        return this.setShortDescription(Texts.of(shortDescription));
    }


    public final Command setShortDescription(final Text shortDescription) {
        this._shortDescription = Optional.of(shortDescription);
        return this;
    }


    public final Command setHelpText(final String helpText) {
        return this.setHelpText(Texts.of(helpText));
    }


    public final Command setHelpText(final Text helpText) {
        this._helpText = Optional.of(helpText);
        return this;
    }


    public final Command setUsageText(final String usageText) {
        return this.setUsageText(Texts.of(usageText));
    }


    public final Command setUsageText(final Text usageText) {
        this._usageText = usageText;
        return this;
    }


    @Override
    public final boolean testPermission(CommandSource source) {
        return source.hasPermission(this._permissionNode);
    }


    @Override
    public final Optional<? extends Text> getShortDescription(CommandSource source) {
        return this._shortDescription;
    }


    @Override
    public final Optional<? extends Text> getHelp(CommandSource source) {
        return this._helpText;
    }


    @Override
    public final Text getUsage(CommandSource source) {
        return this._usageText;
    }


    @Override
    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return Lists.<String> newArrayList();
    }


    @Override
    public abstract Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException;

}
