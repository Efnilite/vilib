package dev.efnilite.vilib;

import dev.efnilite.vilib.inventory.Menu;
import dev.efnilite.vilib.util.Commands;
import dev.efnilite.vilib.util.Logging;
import dev.efnilite.vilib.util.SkullSetter;
import dev.efnilite.vilib.util.elevator.GitElevator;
import dev.efnilite.vilib.util.elevator.VersionComparator;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.Nullable;

public class ViMain extends ViPlugin {

    private Configuration configuration;
    private static ViMain instance;

    @Override
    public void enable() {
        Menu.init();
        Commands.init();
        SkullSetter.init();

        instance = this;

        configuration = new Configuration(this);

        new Metrics(this, 15090);

        logging.info("Enabled vilib " + getDescription().getVersion());
    }

    @Override
    public void disable() { }

    @Override
    public @Nullable GitElevator getElevator() {
        return new GitElevator("Efnilite/vilib", this, VersionComparator.FROM_SEMANTIC,
                configuration.getFile("config").getBoolean("auto-updater"));
    }

    /**
     * Returns the {@link Logging} belonging to this plugin.
     *
     * @return this plugin's {@link Logging} instance.
     */
    public static Logging logging() {
        return getPlugin().logging;
    }

    /**
     * Returns this plugin instance.
     *
     * @return the plugin instance.
     */
    public static ViMain getPlugin() {
        return instance;
    }
}
