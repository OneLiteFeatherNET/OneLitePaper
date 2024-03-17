version = "1.0.0-SNAPSHOT"

dependencies {
    compileOnly(project(":onelitepaper-api"))
    compileOnly(project(":onelitepaper-mojangapi"))
    implementation(project(":onelitepaper-server"))
    implementation(project(":onelitepaper-server", "shadow"))

    file("${project.rootDir}/.gradle/caches/paperweight/jars/minecraft").walkTopDown()
        .filter { it.isFile }
        .filter { it.extension == "jar" }
        .forEach { implementation(files(it)) }
}

tasks.processResources {
    val apiVersion = rootProject.providers.gradleProperty("mcVersion").get()
        .split(".", "-").take(2).joinToString(".")
    val props = mapOf(
        "version" to project.version,
        "apiversion" to "\"$apiVersion\"",
    )
    inputs.properties(props)
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
