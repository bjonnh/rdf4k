import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
}

group = "net.bjonnh"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.eclipse.rdf4j:rdf4j-bom:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-simple:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-runtime:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-queryresultio-text:2.4.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}