package doglong.mythicspawnview.libs;

import doglong.mythicspawnview.libs.dependecy.DependencyManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Objects;

public class Main extends JavaPlugin {

    private static JavaPlugin plugin;
    private static PluginProxy pluginProxy;

    public Main() {
        plugin = this;
        DependencyManager.parsePluginYml();
        try {
            Class<?> clazz = Class.forName(YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(this.getResource("plugin.yml"), "Jar does not contain plugin.yml"))).getString("plugin"));
            if (PluginProxy.class.isAssignableFrom(clazz)) {
                Field instance = clazz.getDeclaredField("INSTANCE");
                instance.setAccessible(true);
                pluginProxy = (PluginProxy) instance.get(null);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onLoad() {
        pluginProxy.initializing();
        pluginProxy.loading();
    }

    @Override
    public void onEnable() {
        pluginProxy.init();
        pluginProxy.starting();
        pluginProxy.enabling();
    }

    @Override
    public void onDisable() {
        pluginProxy.stopping();
        pluginProxy.disabling();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
