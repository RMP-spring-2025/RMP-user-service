//import org.gradle.internal.declarativedsl.parsing.main

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.liquibase.gradle") version "2.2.0"
    application
}

group = "org.healthapp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations {
    liquibaseRuntime
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("io.lettuce:lettuce-core:6.2.3.RELEASE")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.liquibase:liquibase-core:4.17.0")
    liquibaseRuntime("org.liquibase:liquibase-core:4.27.0")
    liquibaseRuntime("org.postgresql:postgresql:42.7.2")
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.1")

}
application {
    mainClass.set("org.healthapp.MainKt")
}
tasks.test {
    useJUnitPlatform()
}
//kotlin {
//    jvmToolchain(21)
//}
tasks.shadowJar {
    archiveBaseName.set("RMP-user-service") // Базовое имя JAR
    archiveClassifier.set("all")            // Добавит суффикс "-all"
    archiveVersion.set("")                 // Убирает версию из имени (опционально)

    manifest {
        attributes["Main-Class"] = "org.healthapp.MainKt" // Укажите ваш главный класс
    }

    mergeServiceFiles() // Важно для совместимости с ServiceLoader (например, для Jackson)
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "changeLogFile" to "src/main/resources/db/changelog/changelog-master.xml",
            "url" to "jdbc:postgresql://localhost:5432/user_db",
            "username" to "user_postgres",
            "password" to "postgres",
            "driver" to "org.postgresql.Driver"
        )
    }
    runList = "main"
}