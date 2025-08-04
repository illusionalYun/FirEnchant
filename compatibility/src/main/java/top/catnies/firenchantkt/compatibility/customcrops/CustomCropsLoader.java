package top.catnies.firenchantkt.compatibility.customcrops;

import net.momirealms.customcrops.api.BukkitCustomCropsPlugin;

public class CustomCropsLoader {

    private static CustomCropsLoader instance;

    private CustomCropsLoader(){}
    public static CustomCropsLoader getInstance() {
        if (instance == null) {
            instance = new CustomCropsLoader();
            BukkitCustomCropsPlugin.getInstance().getIntegrationManager().registerItemProvider(new CustomCropsProvider());
            BukkitCustomCropsPlugin.getInstance().reload();
        }
        return instance;
    }

    private void reload() {

    }

}
