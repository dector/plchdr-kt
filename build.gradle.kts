import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Kotlin.version
}

dependencies {
    implementation(Http4K.core)
    implementation(Http4K.netty)

    implementation("org.jetbrains.skiko:skiko-jvm-runtime-${detectOs()}-x64:0.2.6")
}

group = "space.dector.${rootProject.name}"
version = "0.1-SNAPSHOT"


repositories {
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
