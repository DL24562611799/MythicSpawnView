package doglong.mythicspawnview.libs.dependecy;

import doglong.mythicspawnview.libs.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class DependencyManager {

    /**
     * 解析下载plugin.yml中的依赖
     */
    public static void parsePluginYml() {
        ConfigurationSection libConfigs  = YamlConfiguration.loadConfiguration(new InputStreamReader(requireNonNull(Main.class.getClassLoader().getResourceAsStream("libraries.yml"), "Jar does not contain libraries.yml")));
        DependencyDownloader dd = new DependencyDownloader();
        String folder = libConfigs.getString("libraries-folder");
        if (folder != null) {
            DependencyDownloader.parent = new File(".", folder);
        }
        List<String> repositories = libConfigs.getStringList("repositories");
        if (!repositories.isEmpty()) {
            dd.repositories.clear();
            for (String repository : repositories) {
                dd.addRepository(repository);
            }
        }
        dd.dependencies = libConfigs.getStringList("libraries");
        dd.setup();
    }
}