import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
import org.gradle.plugin.devel.PluginDeclaration
import org.gradle.plugin.use.PluginDependencySpec


inline fun <reified T> PluginDependenciesSpecScope.applyId(t: T): PluginDependencySpec {
    return id(T::class.simpleName!!)
}

fun Project.add(
    container: NamedDomainObjectContainer<PluginDeclaration>, name: String, pluginClass:
    kotlin.reflect.KClass<*>
) {
    container.register(name) {
        id = name
        implementationClass = pluginClass.simpleName
    }
}