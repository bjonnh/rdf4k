import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.dokka.gradle.*

plugins {
    kotlin("jvm") version "1.3.11"
    id("org.jetbrains.dokka") version "0.9.17"
}

group = "net.bjonnh"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8:1.3.11"))
    compile(kotlin("reflect:1.3.11"))
    compile("org.eclipse.rdf4j:rdf4j-bom:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-runtime:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-queryresultio-text:2.4.2")
    compile("org.eclipse.rdf4j:rdf4j-sparqlbuilder:2.4.2")
    compile("org.jetbrains.dokka:dokka-gradle-plugin:0.9.17")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val githubRepo = "https://github.com/bjonnh/rdf4k"

tasks.withType<DokkaTask> {
    val src = "src/main"
    val out = "$projectDir/docs"

    doFirst {
        println("Cleaning doc directory ${out}...")
        project.delete(fileTree(out) {
            exclude("logos/**", "templates/**")
        })
    }

    moduleName = ""
    sourceDirs = files(src)
    outputFormat = "html"
    outputDirectory = out
    skipEmptyPackages = true
    jdkVersion = 8
    includes = listOf("README.md")
    val mapping = LinkMapping().apply {
        dir = src
        url = "${githubRepo}/blob/master/$src"
        suffix = "#L"
    }
    linkMappings = arrayListOf(mapping)
    description = "Generate ${project.name} v$version docs in HTML."

    doLast {
        println("Generated HTML format docs to ${outputDirectory}")
    }
}
