package me.pokerman99.AdminShop;

/**
 * Created by troyg on 1/29/2018.
 */

import com.google.inject.Inject;
import me.pokerman99.AdminShop.commands.buyCommand;
import me.pokerman99.AdminShop.commands.buyRemoveLocationCommand;
import me.pokerman99.AdminShop.commands.sellCommand;
import me.pokerman99.AdminShop.commands.sellRemoveLocationCommand;
import me.pokerman99.AdminShop.listeners.buyRightClickListener;
import me.pokerman99.AdminShop.listeners.sellRightClickListener;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

@Plugin(id = "adminshopec",
        name = "AdminShopEC",
        version = "1.0",
        description = "Plugin for Justin's servers providing an admin shop")

public class Main implements CommandExecutor{

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path ConfigDir;

    @Inject
    public PluginContainer plugin;
    public PluginContainer getPlugin() {
        return this.plugin;
    }

    public static CommentedConfigurationNode rootNode;

    public static CommentedConfigurationNode config() {
        return rootNode;
    }

    public void save() throws IOException {
        loader.save(rootNode);
    }

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static EconomyService economyService;

    public Main() {
        super();
    }

    @Listener
    public void onPreInit(GameStartedServerEvent event){
        Optional<EconomyService> optionalEconomyService = Sponge.getServiceManager().provide(EconomyService.class);
        if (!optionalEconomyService.isPresent())
        {getLogger().severe("There is no Economy Plugin installed on this Server! The money reward will not work!"); return;
        }else{economyService = optionalEconomyService.get();}

        try {rootNode = loader.load();} catch (IOException e){e.printStackTrace();}

        rootNode.getNode("config-version").setValue(1.0);

        try {save();} catch (IOException e){e.printStackTrace();}

        CommandSpec buyCommand = CommandSpec.builder()
                .executor(new buyCommand(this))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("cost"))))
                .permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, buyCommand, "setbuy");

        CommandSpec buyRemoveLocationCommand = CommandSpec.builder()
                .executor(new buyRemoveLocationCommand(this))
                .permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, buyRemoveLocationCommand, "removebuylocation");
        Sponge.getEventManager().registerListeners(this, new buyRightClickListener(this));


        CommandSpec sellCommand = CommandSpec.builder()
                .executor(new sellCommand(this))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("cost"))))
                .permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, sellCommand, "setsell");

        CommandSpec sellRemoveLocationCommand = CommandSpec.builder()
                .executor(new sellRemoveLocationCommand(this))
                .permission("adminshopec.admin")
                .build();
        Sponge.getCommandManager().register(this, sellRemoveLocationCommand, "removeselllocation");
        Sponge.getEventManager().registerListeners(this, new sellRightClickListener(this));

        CommandSpec reload = CommandSpec.builder()
                .executor(this)
                .permission("adminshopec.reload")
                .build();
        Sponge.getCommandManager().register(this, reload, "ashopreload");

    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try{loader.load();} catch (IOException e){e.printStackTrace();}
        Utils.sendMessage(src, "&aReloaded");
        return CommandResult.success();
    }
}
