package top.catnies.firenchantkt.compatibility.customfishing;

import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;

public class CustomFishingLoader {

    private static CustomFishingLoader instance;

    private CustomFishingLoader(){}
    public static CustomFishingLoader getInstance() {
        if (instance == null) {
            instance = new CustomFishingLoader();
            BukkitCustomFishingPlugin.getInstance().getIntegrationManager().registerItemProvider(new CustomFishingProvider());
            BukkitCustomFishingPlugin.getInstance().reload();
        }
        return instance;
    }

    private void reload() {

    }

}
