package top.catnies.firenchantkt;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class FirEnchantPluginLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository(new RemoteRepository.Builder("alibaba-central", "default", "https://maven.aliyun.com/repository/public").build());
        resolver.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io").build());

        // Kotlin STD
        resolver.addDependency(new Dependency(new DefaultArtifact("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.21"), null));

        // RTag
        resolver.addDependency(new Dependency(new DefaultArtifact("com.saicone.rtag:rtag:1.5.11"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.saicone.rtag:rtag-item:1.5.11"), null));

        // MYSQL
        resolver.addDependency(new Dependency(new DefaultArtifact("com.j256.ormlite:ormlite-core:6.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.j256.ormlite:ormlite-jdbc:6.1"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.zaxxer:HikariCP:6.1.0"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("com.h2database:h2:2.3.232"), null));

        // REDIS
        resolver.addDependency(new Dependency(new DefaultArtifact("io.lettuce:lettuce-core:6.5.5.RELEASE"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("io.projectreactor:reactor-core:3.6.6"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("org.reactivestreams:reactive-streams:1.0.4"), null));

        classpathBuilder.addLibrary(resolver);
    }

}
