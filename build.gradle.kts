
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version("2.2.20")

    id("dev.architectury.loom") version("1.17-SNAPSHOT") apply false //1.17
    id("architectury-plugin") version("3.5-SNAPSHOT") apply false //3.5

}

/*
* ./gradlew --version

Welcome to Gradle 8.14.3!

Here are the highlights of this release:
 - Java 24 support
 - GraalVM Native Image toolchain selection
 - Enhancements to test reporting
 - Build Authoring improvements

For more details see https://docs.gradle.org/8.14.3/release-notes.html


------------------------------------------------------------
Gradle 8.14.3
------------------------------------------------------------

Build time:    2025-07-04 13:15:44 UTC
Revision:      e5ee1df3d88b8ca3a8074787a94f373e3090e1db

Kotlin:        2.0.21
Groovy:        3.0.24
Ant:           Apache Ant(TM) version 1.10.15 compiled on August 25 2024
Launcher JVM:  21.0.5 (Eclipse Adoptium 21.0.5+11-LTS)
Daemon JVM:    C:\Program Files\Eclipse Adoptium\jdk-21.0.5.11-hotspot (no JDK specified, using current Java home)
OS:            Windows 11 10.0 amd64*/

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    version = project.properties["mod_version"]!!
    group = project.properties["maven_group"]!!

    repositories {
        mavenCentral()
        maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        //maven("https://maven.impactdev.net/repository/development/")
        maven("https://artefacts.cobblemon.com/releases/")
        maven("https://maven.neoforged.net/releases")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
        maven("https://api.modrinth.com/maven")
        maven("https://jitpack.io")
        maven("https://maven.isxander.dev/releases") {
            name = "Xander Maven"
        }
        maven("https://maven.terraformersmc.com/"){
            name = "Terraformers"
        }

    }

    tasks {
        test {
            useJUnitPlatform()
        }

        java {
            withSourcesJar()
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        compileJava {
            options.release = 21
        }

        compileKotlin {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }
}
