package me.pokerman99.AdminShop.listeners;

import me.pokerman99.AdminShop.Main;
import me.pokerman99.AdminShop.Utils;
import me.pokerman99.AdminShop.commands.buyCommand;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;

import java.io.IOException;

public class buyRightClickListener {

    public Main plugin;

    public buyRightClickListener(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    @Listener
    public void onRightClick(InteractBlockEvent e, @First Player player){
        if (e.getTargetBlock().getState().getType() == BlockTypes.WALL_SIGN || e.getTargetBlock().getState().getType() == BlockTypes.STANDING_SIGN){
            String bposition = e.getTargetBlock().getPosition().toString();
            String dim = Utils.getDim(e.getTargetBlock().getWorldUniqueId()).toString();
            String info = bposition + " " + dim;
            if (buyCommand.users.contains(player.getUniqueId())) {
                int max = plugin.rootNode.getNode("buy", "locations").getChildrenMap().size() + 1;
                if (Utils.onCheckLocationBuy(info, max) == true) {
                    Utils.sendMessage(player, "&cThere's already a shop at this location!");
                    return;
                }
                plugin.rootNode.getNode("buy", "locations", "location-" + max).setValue(info);
                try{plugin.save();} catch (IOException e1) {e1.printStackTrace();}
                Utils.sendMessage(player, "&aSuccessfully set location for shop " + max);
                buyCommand.users.remove(player.getUniqueId());
                return;
            }
            int max = plugin.rootNode.getNode("buy", "locations").getChildrenMap().size();
            for (int x = 1; max >= x; x++){
                String temp = plugin.rootNode.getNode("buy", "locations", "location-" + x).getString();
                if (temp.equals(info)){

                    double cost = plugin.rootNode.getNode("buy", "cost", "cost-" + x).getDouble();
                    int amount = plugin.rootNode.getNode("buy", "amount", "amount-" + x).getInt();
                    String item = plugin.rootNode.getNode("buy", "items", "item-" + x).getString();
                    ItemStack itemstack = Utils.serialize(item).get();
                    if (Utils.getPlayerBal(player) < cost) {
                        Utils.sendMessage(player, "&cYou don't have enough money for this item!");
                        return;
                    }
                    if (player.getInventory().size() >= 36){
                        if (!player.getInventory().contains(itemstack)) {
                            Utils.sendMessage(player, "&cYou don't have enough room in your inventory for this item!");
                            return;
                        }
                    }
                    if (!Utils.serialize(item).isPresent()){
                        Utils.sendMessage(player, "&cTransaction failed! Consult a staff member");
                        return;
                    }
                    itemstack.setQuantity(amount);
                    player.getInventory().offer(itemstack);
                    Utils.onWithdraw(player, cost);
                    if (amount == 1){
                        Utils.sendMessage(player, "&aSuccessfully bought a " + itemstack.getItem().getName() + " for $" + cost + "!".replace("minecraft:", "").replace("pixelmon:", ""));
                    } else {
                        Utils.sendMessage(player, "&aSuccessfully bought " + amount + itemstack.getItem().getName() + " for $" + cost + "!".replace("minecraft:", "").replace("pixelmon:", ""));
                    }

                    break;
                }
            }
            return;
        }
    }


}
