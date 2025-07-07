package top.catnies.firenchantkt

import io.papermc.paper.plugin.loader.PluginClasspathBuilder
import io.papermc.paper.plugin.loader.PluginLoader
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver
import org.eclipse.aether.artifact.DefaultArtifact
import org.eclipse.aether.graph.Dependency
import org.eclipse.aether.repository.RemoteRepository

class FirEnchantPluginLoader: PluginLoader {

    // 依赖管理
    override fun classloader(classpathBuilder: PluginClasspathBuilder) {
        val resolver = MavenLibraryResolver()
        resolver.addRepository(RemoteRepository.Builder("alibaba-central", "default", "https://maven.aliyun.com/repository/public").build())
        resolver.addRepository(RemoteRepository.Builder("tsinghua-central", "default", "https://repo.maven.apache.org/maven2/").build())

        // MYSQL
        resolver.addDependency(Dependency(DefaultArtifact("com.j256.ormlite:ormlite-core:6.1"), null))
        resolver.addDependency(Dependency(DefaultArtifact("com.j256.ormlite:ormlite-jdbc:6.1"), null))
        resolver.addDependency(Dependency(DefaultArtifact("com.zaxxer:HikariCP:6.1.0"), null))
        resolver.addDependency(Dependency(DefaultArtifact("com.h2database:h2:2.3.232"), null))

        // REDIS
        resolver.addDependency(Dependency(DefaultArtifact("io.lettuce:lettuce-core:6.5.5.RELEASE"), null))
        resolver.addDependency(Dependency(DefaultArtifact("io.projectreactor:reactor-core:3.6.6"), null))
        resolver.addDependency(Dependency(DefaultArtifact("org.reactivestreams:reactive-streams:1.0.4"), null))

        classpathBuilder.addLibrary(resolver)
    }

}