import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.customImplementation(dependencies: List<Dependency>) {
    dependencies.forEach {
        configuration(
            when (it) {
                is Dependency.Ksp -> {
                    println("ksp: ${it.full()}")
                    "ksp"
                }

                else -> {
                    println("implementation: ${it.full()}")
                    "implementation"
                }
            },
            it
        )
    }
}

fun DependencyHandler.project(projectName: String) {
    println("implementation project: [$projectName]")
    add("implementation", project(mapOf("path" to projectName)))
}

fun DependencyHandler.api(projectName: String){
    println("api project: [$projectName]")
    add("api", project(mapOf("path" to projectName)))
}

private fun DependencyHandler.configuration(name: String, dependency: Dependency) {
    add(name, dependency.full())
}