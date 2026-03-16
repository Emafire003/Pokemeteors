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
    modCompileOnly("maven.modrinth:ohmymeteors:${property("omm_fabric")}")
    //TODO make it right
    modCompileOnly(files("run/libs/structureplacerapi-2.1.0+1.21.1.jar"))
    //modCompileOnly("maven.modrinth:structureplacerapi:${property("structureplacerapi_fabric")}") //StructurePlacerAPI
    modCompileOnly("maven.modrinth:particleanimationlib:${property("pal_fabric")}") //PAL
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