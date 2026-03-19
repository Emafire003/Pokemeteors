plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    common("neoforge", "fabric")
}

loom {
    silentMojangMappingsLicense()
}

dependencies {

    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("com.cobblemon:mod:${property("cobblemon_version")}") { isTransitive = false }

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")

    //OhMyMeteors && dependencies
    modCompileOnly((files("run/libs/ohmymeteors-1.3.1+1.21-dev.jar")))
    //modCompileOnly("maven.modrinth:ohmymeteors:${property("omm_version")}")
    modCompileOnly("maven.modrinth:structureplacerapi:${property("structureplacerapi_version")}") //StructurePlacerAPI
    modCompileOnly("maven.modrinth:particleanimationlib:${property("pal_version")}") //PAL
    implementation("com.github.PiTheGuy:SchemConvert:master-SNAPSHOT") //SchemConvert

    //TODO maybe I need an implementation
    compileOnly("org.spongepowered:mixin:0.8.5")
    // fabric and neoforge both bundle mixinextras, so it is safe to use it in common
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.3.5")
}

tasks.test {
    useJUnitPlatform()
}