package dk.sdu.mmmi.cbse.main;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPMSHandler: Provides logic to handle Java Platform Module System (JPMS) operations.
 */
public class JPMSHandler {

    /**
     * Creates a module layer for plugins.
     *
     * @param pluginsDirectory The directory where the plugins are located.
     * @return A module layer that contains the plugins.
     */
    public static ModuleLayer createModuleLayer(Path pluginsDirectory) {
        // ModuleFinder that searches for plugins in the given directory
        ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDirectory);

        // Find all names of all found plugin modules and collect them in a list
        List<String> plugins = pluginsFinder
                .findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .collect(Collectors.toList());

        // Create a configuration for the module system
        // This configuration resolves the modules found by the ModuleFinder
        // It verifies that the module graph (dependencies between modules) is correct
        Configuration pluginsConfiguration = ModuleLayer
                .boot()
                .configuration()
                .resolve(pluginsFinder,ModuleFinder.of(), plugins);

        // Create a module layer for plugins using the system class loader
        return ModuleLayer.boot().defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());
    }
}
