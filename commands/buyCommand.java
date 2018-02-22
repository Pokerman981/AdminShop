package me.pokerman99.AdminShop.commands;

import me.pokerman99.AdminShop.Main;
import me.pokerman99.AdminShop.Utils;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class buyCommand implements CommandExecutor {
    public Main plugin;

    public buyCommand(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    public static List<UUID> users = new ArrayList<>();
    public static List<String> shop = new ArrayList<>();


    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        Optional<Integer> amount = args.getOne("amount");
        Optional<Integer> cost = args.getOne("cost");
        Optional<ItemStack> handitem = player.getItemInHand(HandTypes.MAIN_HAND);
        if (!handitem.isPresent()) {
            Utils.sendMessage(player, "&cYou must be holding a item in your hand to set a shop");
            shop.clear();
            return CommandResult.empty();
        }
        shop.add(String.valueOf(amount.get()));
        shop.add(String.valueOf(cost.get()));
        shop.add(handitem.get().getType().getName());
        users.add(player.getUniqueId());
        Utils.sendMessage(player, "&aRight click a sign to finish setting the shop");
        return CommandResult.success();

        /*Player player = (Player) src;
        Optional<Integer> amount = args.getOne("amount");
        if (!amount.isPresent()) {
            Utils.sendMessage(player, "&cYou must supply an amount");
            return CommandResult.empty();
        }
        Optional<Integer> cost = args.getOne("cost");
        if (!cost.isPresent()) {
            Utils.sendMessage(player, "&cYou must supply a cost");
            return CommandResult.empty();
        }
        Optional<ItemStack> handitem = player.getItemInHand(HandTypes.MAIN_HAND);
        if (!handitem.isPresent()) {
            Utils.sendMessage(player, "&cYou must be holding a item in your hand to set a shop!");
            return CommandResult.empty();
        }
        Optional<String> deser = Utils.deserialize(handitem.get());
        if (!deser.isPresent()){
            Utils.sendMessage(player, "&cFailed to serialize item, try again.");
            return CommandResult.empty();
        }
        if (plugin.rootNode.getNode("buy", "items").isVirtual()){
            plugin.rootNode.getNode("buy", "items", "item-" + 1).setValue(deser.get());
            plugin.rootNode.getNode("buy", "cost", "cost-" + 1).setValue(cost.get());
            plugin.rootNode.getNode("buy", "amount", "amount-" + 1).setValue(amount.get());
            try{plugin.save();} catch (IOException e) {e.printStackTrace();}

            users.add(player.getUniqueId());

            Utils.sendMessage(player, "&aRight click a sign to set the location");
            return CommandResult.success();
        }
        int max = plugin.rootNode.getNode("buy", "items").getChildrenMap().size() + 1;

        plugin.rootNode.getNode("buy", "items", "item-" + max).setValue(deser.get());
        plugin.rootNode.getNode("buy", "cost", "cost-" + max).setValue(cost.get());
        plugin.rootNode.getNode("buy", "amount", "amount-" + max).setValue(amount.get());
        try{plugin.save();} catch (IOException e) {e.printStackTrace();}

        users.add(player.getUniqueId());

        Utils.sendMessage(player, "&aRight click a sign to set the location");
        return CommandResult.success();*/
    }
}

