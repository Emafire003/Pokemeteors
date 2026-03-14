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
    modCompileOnly(files("run/libs/structureplacerapi-2.1.0+1.21.1+mjmps.jar"))
    //modCompileOnly("maven.modrinth:structureplacerapi:${property("structureplacerapi_fabric")}") //StructurePlacerAPI
    modCompileOnly("maven.modrinth:particleanimationlib:${property("pal_fabric")}") //PAL
    implementation("com.github.PiTheGuy:SchemConvert:master-SNAPSHOT") //SchemConvert
}

tasks.test {
    useJUnitPlatform()
}