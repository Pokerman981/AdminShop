package me.pokerman99.AdminShop;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.DimensionType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class Utils {

    public Main plugin;

    public Utils(Main pluginInstance) {
        this.plugin = pluginInstance;
    }

    public static final Currency cur = Main.economyService.getDefaultCurrency();

    public static double getPlayerBal(Player player){
        BigDecimal bal = Main.economyService.getOrCreateAccount(player.getUniqueId()).get().getBalances().get(cur);
        return bal.doubleValue();
    }

    public static void onWithdraw(Player player, double num){
        BigDecimal value = new BigDecimal(num);
        Main.economyService.getOrCreateAccount(player.getUniqueId()).get().withdraw(cur , value, Sponge.getCauseStackManager().getCurrentCause());
    }

    public static void onDeposite(Player player, double num){
        BigDecimal value = new BigDecimal(num);
        Main.economyService.getOrCreateAccount(player.getUniqueId()).get().deposit(cur, value, Sponge.getCauseStackManager().getCurrentCause());
    }

    public static String color(String string) {
        return TextSerializers.FORMATTING_CODE.serialize(Text.of(string));
    }

    public static void sendMessage(CommandSource sender, String message) {
        if (sender == null) { return; }
        sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(color(message)));
    }

    public static Optional<String> deserialize(ItemStack stack) {
        try {
            StringWriter sink = new StringWriter();
            ConfigurationLoader loader = (HoconConfigurationLoader.builder().setSink(() -> new BufferedWriter(sink))).build();
            ConfigurationNode node = loader.createEmptyNode();
            node.setValue(TypeToken.of(ItemStack.class), stack);
            loader.save(node);
            return Optional.of(sink.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<ItemStack> serialize(String itemString) {
        try {
            StringReader source = new StringReader(itemString);
            ConfigurationLoader loader = GsonConfigurationLoader.builder().setSource(() -> new BufferedReader(source)).build();
            ConfigurationNode node = loader.load();

            return Optional.of(node.getValue(TypeToken.of(ItemStack.class)));
        } catch (Exception e1) {
            try {
                StringReader source = new StringReader(itemString);
                ConfigurationLoader loader = HoconConfigurationLoader.builder().setSource(() -> new BufferedReader(source)).build();
                ConfigurationNode node = loader.load();
                return Optional.of(node.getValue(TypeToken.of(ItemStack.class)));
            } catch (Exception e2) {
                e2.printStackTrace();
                return Optional.empty();
            }
        }
    }

    public static DimensionType getDim(UUID uuid){
        DimensionType dim = Sponge.getServer().getWorld(uuid).get().getDimension().getType();
        return dim;
    }

    public static boolean onCheckLocationBuy(String string, int max){
        boolean temp2 = false;
        for (int x = 1; max >= x; x++) {
            String temp = Main.rootNode.getNode("buy", "locations", "location-" + x).getString();
            if (string.equals(temp)){
                temp2 = true;
                break;
            }
        }
        return temp2;
    }

    public static boolean onCheckLocationSell(String string, int max){
        boolean temp2 = false;
        for (int x = 1; max >= x; x++) {
            String temp = Main.rootNode.getNode("sell", "locations", "location-" + x).getString();
            if (string.equals(temp)){
                temp2 = true;
                break;
            }
        }
        return temp2;
    }

}
