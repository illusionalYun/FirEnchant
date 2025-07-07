package top.catnies.firenchantkt.library;

import cn.chengzhiya.mhdflibrary.entity.DependencyConfig;
import cn.chengzhiya.mhdflibrary.entity.RelocateConfig;
import cn.chengzhiya.mhdflibrary.entity.RepositoryConfig;
import cn.chengzhiya.mhdflibrary.manager.LoggerManager;
import top.catnies.firenchantkt.FirEnchantPlugin;
import cn.chengzhiya.mhdflibrary.MHDFLibrary;

import java.io.File;

public class LibraryManager {
    private static LibraryManager instance;

    private final RepositoryConfig chengzhiMeow = new RepositoryConfig("https://maven.chengzhimeow.cn/releases");
    private final RepositoryConfig repositoryConfig = new RepositoryConfig("https://repo.xenondevs.xyz/releases");

    public static LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
            instance.load();
        }

        return instance;
    }

    public void load() {
        MHDFLibrary mhdfLibrary = new MHDFLibrary(
                FirEnchantPlugin.class,
                new LibraryLoggerManager(),
                "top.catnies.firenchantkt.libs",
                new File(FirEnchantPlugin.getInstance().getDataFolder(), "libs")
        );

        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("cn{}chengzhiya"),
                "MHDF-Scheduler",
                "1.0.1",
                chengzhiMeow,
                new RelocateConfig(true)
        ));

        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                "xyz{}xenondevs{}invui",
                "invui",
                "1.46",
                this.repositoryConfig,
                new RelocateConfig(true)
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}j256{}ormlite"),
                "ormlite-core",
                "6.1",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));

        // 数据库
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}j256{}ormlite"),
                "ormlite-core",
                "6.1",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}j256{}ormlite"),
                "ormlite-jdbc",
                "6.1",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}zaxxer"),
                "HikariCP",
                "6.1.0",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}h2database"),
                "h2",
                "2.3.232",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true, false,
                        handleString("org{}h2")
                )
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("com{}mysql"),
                "mysql-connector-j",
                "9.1.0",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));

        // redis
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("io{}lettuce"),
                "lettuce-core",
                "6.5.5.RELEASE",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("io{}projectreactor"),
                "reactor-core",
                "3.6.6",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true, true,
                        "reactor"
                )
        ));
        mhdfLibrary.addDependencyConfig(new DependencyConfig(
                handleString("org{}reactivestreams"),
                "reactive-streams",
                "1.0.4",
                MHDFLibrary.mavenCenterMirror,
                new RelocateConfig(true)
        ));

        mhdfLibrary.downloadDependencies();
        mhdfLibrary.loadDependencies();
    }

    /**
     * 处理文本
     *
     * @param string 文本
     * @return 处理后的文本
     */
    private String handleString(String string) {
        return string.replace("{}", ".");
    }

    static class LibraryLoggerManager implements LoggerManager {
        @Override
        public void log(String string) {
            FirEnchantPlugin.getInstance().getLogger().info(string);
        }
    }
}
