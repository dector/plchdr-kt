import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Kotlin.version
}

dependencies {
}

group = "space.dector.${rootProject.name}"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
