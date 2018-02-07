package me.pokerman99.AdminShop.commands;

import me.pokerman99.AdminShop.Main;
import me.pokerman99.AdminShop.Utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.io.IOException;

public class buyRemoveLocationCommand implements CommandExecutor{
    public Main plugin;

    public buyRemoveLocationCommand(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (Main.rootNode.getNode("buy").isVirtual()) return CommandResult.empty();
        int size = plugin.rootNode.getNode("buy", "items").getChildrenMap().size();
        plugin.rootNode.getNode("buy", "items", "item-" + size).setValue(null);
        plugin.rootNode.getNode("buy", "cost", "cost-" + size).setValue(null);
        plugin.rootNode.getNode("buy", "amount", "amount-" + size).setValue(null);
        plugin.rootNode.getNode("buy", "locations", "location-" + size).setValue(null);
        try{plugin.save();} catch (IOException e){ e.printStackTrace();}
        Utils.sendMessage(src, "&aSuccessfully removed shop " + size);
        return CommandResult.success();
    }
}
