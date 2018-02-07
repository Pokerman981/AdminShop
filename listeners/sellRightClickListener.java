package me.pokerman99.AdminShop.listeners;

import me.pokerman99.AdminShop.Main;
import me.pokerman99.AdminShop.Utils;
import me.pokerman99.AdminShop.commands.sellCommand;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;

import java.io.IOException;
import java.util.Optional;

public class sellRightClickListener {
    public Main plugin;

    public sellRightClickListener(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    @Listener
    public void onRightClick(InteractBlockEvent e, @First Player player){
        if (e.getTargetBlock().getState().getType() == BlockTypes.WALL_SIGN || e.getTargetBlock().getState().getType() == BlockTypes.STANDING_SIGN){
            String bposition = e.getTargetBlock().getPosition().toString();
            String dim = Utils.getDim(e.getTargetBlock().getWorldUniqueId()).toString();
            String info = bposition + " " + dim;
            if (sellCommand.users.contains(player.getUniqueId())) {
                int max = plugin.rootNode.getNode("sell", "locations").getChildrenMap().size() + 1;
                if (Utils.onCheckLocationSell(info, max) == true) {
                    Utils.sendMessage(player, "&cThere's already a shop at this location!");
                    return;
                }
                plugin.rootNode.getNode("sell", "locations", "location-" + max).setValue(info);
                try{plugin.save();} catch (IOException e1) {e1.printStackTrace();}
                Utils.sendMessage(player, "&aSuccessfully set location for shop " + max);
                sellCommand.users.remove(player.getUniqueId());
                return;
            }
            int max = plugin.rootNode.getNode("sell", "locations").getChildrenMap().size();
            for (int x = 1; max >= x; x++){
                String temp = plugin.rootNode.getNode("sell", "locations", "location-" + x).getString();
                if (temp.equals(info)){
                    double cost = plugin.rootNode.getNode("sell", "cost", "cost-" + x).getDouble();
                    int amount = plugin.rootNode.getNode("sell", "amount", "amount-" + x).getInt();
                    String item = plugin.rootNode.getNode("sell", "items", "item-" + x).getString();
                    Optional<ItemStack> hand = player.getItemInHand(HandTypes.MAIN_HAND);
                    Optional<ItemStack> configitem = Utils.serialize(item);
                    Optional<ItemStack> handtemp = player.getItemInHand(HandTypes.MAIN_HAND);
                    Optional<ItemStack> configitemtemp = Utils.serialize(item);
                    if (!hand.isPresent()){
                        Utils.sendMessage(player, "&cYou must be holding a item to sell!");
                        return;
                    }
                    if (!configitem.isPresent()){
                        Utils.sendMessage(player, "&cTransaction failed! Consult a staff member");
                        return;
                    }
                    if (!handtemp.isPresent()){
                        Utils.sendMessage(player, "&cTransaction failed! Consult a staff member. Error code:0x0003");
                        return;
                    }
                    if (!configitemtemp.isPresent()){
                        Utils.sendMessage(player, "&cTransaction failed! Consult a staff member. Error code:0x0004");
                        return;
                    }
                    handtemp.get().setQuantity(1);
                    configitem.get().setQuantity(1);
                    if (!handtemp.get().equalTo(configitem.get())){
                        Utils.sendMessage(player, "&cYou must be holding the correct item you are trying to sell");
                        return;
                    }
                    if (player.getItemInHand(HandTypes.MAIN_HAND).get().getQuantity() < amount){
                        Utils.sendMessage(player, "&cYou must have at least " + amount + " of this item to sell");
                        return;
                    }
                    int handamount = hand.get().getQuantity() - amount;
                    hand.get().setQuantity(handamount);
                    player.setItemInHand(HandTypes.MAIN_HAND, hand.get());
                    Utils.onDeposite(player, cost);
                    if (amount == 1){
                        Utils.sendMessage(player, "&aSuccessfully sold a " + hand.get().getItem().getName() + " for $" + cost + "!".replaceAll("minecraft:", "").replaceAll("pixelmon:", ""));
                    } else {
                        Utils.sendMessage(player, "&aSuccessfully sold " + amount + hand.get().getItem().getName() + " for $" + cost + "!".replaceAll("minecraft:", "").replaceAll("pixelmon:", ""));
                    }
                    break;
                }
            }
            return;
        }
    }
}
