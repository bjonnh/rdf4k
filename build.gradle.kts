import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.publish.maven.MavenPom
import org.jetbrains.dokka.gradle.*

val kotlin_version = "1.3.11"
val rdf4j_version = "2.4.2"

plugins {
    kotlin("jvm") version "1.3.11"
    id("org.jetbrains.dokka") version "0.9.17"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "net.nprod"
version = "0.0.3"
val artifactID = "rdf4k"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlin_version))
    compile(kotlin("reflect", kotlin_version))
    compile("org.eclipse.rdf4j", "rdf4j-bom", rdf4j_version)
    compile("org.eclipse.rdf4j", "rdf4j-runtime", rdf4j_version)
    compile("org.eclipse.rdf4j", "rdf4j-queryresultio-text", rdf4j_version)
    compile("org.eclipse.rdf4j", "rdf4j-sparqlbuilder", rdf4j_version)
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.9.17")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val githubRepo = "https://github.com/bjonnh/rdf4k"

val dokkaJavadoc = task<DokkaTask>("dokkaJavadoc") {
    val src = "src/main"
    outputFormat = "javadoc"
    outputDirectory = "$projectDir/javadoc"
    skipEmptyPackages = true
    jdkVersion = 8
    /*val mapping = LinkMapping().apply {
        dir = src
        url = "${githubRepo}/blob/master/$src"
        suffix = "#L"
    }
    linkMappings = arrayListOf(mapping)*/
    sourceDirs = files(src)
    moduleName = ""
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn("dokkaJavadoc")
    classifier = "javadoc"
    from(tasks["dokkaJavadoc"])
}


val dokkaHtmldoc = task<DokkaTask>("dokkaHtmldoc") {
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

tasks {
    withType<BintrayUploadTask> {
        dependsOn("build")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

}


val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource.sourceDirectories.files)
}


fun findProperty(s: String) = project.findProperty(s) as String?

bintray {
    user = findProperty("bintrayUser")
    key = findProperty("bintrayApiKey")
    publish = true
    setPublications("BintrayRelease")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "RDF4K"
        name = "RDF4K"
        websiteUrl = "https://www.github.com/bjonnh/rdf4k"
        githubRepo = "bjonnh/rdf4k"
        vcsUrl = "https://github.com/bjonnh/rdf4k"
        description = "A wrapper around RDF4J for Kotlin."
        setLabels("kotlin", "rdf4j", "linkeddata")
        setLicenses("EPL-2.0", "GPL-3.0")
        desc = description
    })
}


publishing.publications.create<MavenPublication>("BintrayRelease") {
    from(components["java"])
    artifact(sourcesJar)
    artifact(javadocJar)
    groupId = project.group.toString()
    artifactId = project.name
    version = project.version.toString()
}
