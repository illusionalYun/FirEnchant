package top.catnies.firenchantkt;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

// TODO 版本信息是否可以从配置文件读取?
public class FirEnchantPluginLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository(new RemoteRepository.Builder("alibaba-central", "default", "https://maven.aliyun.com/repository/public").build());
        resolver.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io").build());

        // Kotlin STD, Reflect, Coroutines
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.21"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-reflect:2.1.21"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlinx:kotlinx-coroutines-jdk9:1.8.1"), null));
//        resolver.addDependency(new Dependency(new DefaultArtifact("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.22.0"), null));
//        resolver.addDependency(new Dependency(new DefaultArtifact("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.22.0"), null));

        // RTag
        resolver.addDependency(new Dependency(new DefaultArtifact("com.saicone.rtag:rtag:1.5.11"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.saicone.rtag:rtag-item:1.5.11"), null));

        // MYSQL
        resolver.addDependency(new Dependency(new DefaultArtifact("com.j256.ormlite:ormlite-core:6.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.j256.ormlite:ormlite-jdbc:6.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.zaxxer:HikariCP:6.1.0"), null));

        // REDIS
        resolver.addDependency(new Dependency(new DefaultArtifact("io.lettuce:lettuce-core:6.5.5.RELEASE"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.projectreactor:reactor-core:3.6.6"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.reactivestreams:reactive-streams:1.0.4"), null));

        classpathBuilder.addLibrary(resolver);
    }

}
