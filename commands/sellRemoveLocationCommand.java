package me.pokerman99.AdminShop.commands;

import me.pokerman99.AdminShop.Main;
import me.pokerman99.AdminShop.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.io.IOException;

public class sellRemoveLocationCommand implements CommandExecutor{
    public Main plugin;

    public sellRemoveLocationCommand(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (Main.rootNode.getNode("sell").isVirtual()) return CommandResult.empty();
        int size = plugin.rootNode.getNode("sell", "items").getChildrenMap().size();
        plugin.rootNode.getNode("sell", "items", "item-" + size).setValue(null);
        plugin.rootNode.getNode("sell", "cost", "cost-" + size).setValue(null);
        plugin.rootNode.getNode("sell", "amount", "amount-" + size).setValue(null);
        plugin.rootNode.getNode("sell", "locations", "location-" + size).setValue(null);
        try{plugin.save();} catch (IOException e){ e.printStackTrace();}
        Utils.sendMessage(src, "&aSuccessfully removed shop " + size);
        return CommandResult.success();
    }
}
