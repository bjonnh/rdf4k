/*
 * Copyright (c) 2019 Jonathan Bisson
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

import net.nprod.rdf4k.build.configureSpotless
import net.nprod.rdf4k.build.configureTesting
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Properties

val kotlinVersion = "1.3.60"
val rdf4jVersion = "3.0.2"

plugins {
    kotlin("jvm") version "1.3.60"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    apply { id("com.github.ben-manes.versions") version "0.25.0" }
}

group = "net.nprod"
version = "0.1.1"
val artifactID = "rdf4k"

buildscript {
    val kotlinVersion = "1.3.60"
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

repositories {
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
    compile("org.eclipse.rdf4j", "rdf4j-bom", rdf4jVersion)
    compile("org.eclipse.rdf4j", "rdf4j-runtime", rdf4jVersion)
    compile("org.eclipse.rdf4j", "rdf4j-queryresultio-text", rdf4jVersion)
    compile("org.eclipse.rdf4j", "rdf4j-sparqlbuilder", rdf4jVersion)
    compileOnly("org.jetbrains.dokka:dokka-gradle-plugin:0.10.0")
    testCompile("org.junit.jupiter", "junit-jupiter", "5.5.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {}
}

val githubRepo = "https://github.com/bjonnh/rdf4k"

val dokkaJavadoc = task<DokkaTask>("dokkaJavadoc") {
    outputFormat = "javadoc"
    outputDirectory = "$projectDir/javadoc"
    configuration {
        skipEmptyPackages = true
        jdkVersion = 8
        moduleName = ""
        sourceRoot {
            path = "src"
        }
    }

    /*val mapping = LinkMapping().apply {
        dir = src
        url = "${githubRepo}/blob/master/$src"
        suffix = "#L"
    }
    linkMappings = arrayListOf(mapping)*/
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn("dokkaJavadoc")
    classifier = "javadoc"
    from(tasks["dokkaJavadoc"])
}

val dokkaHtmldoc = task<DokkaTask>("dokkaHtmldoc") {
    val src = "src/"
    val out = "$projectDir/docs"

    doFirst {
        println("Cleaning doc directory $out...")
        project.delete(fileTree(out) {
            exclude("logos/**", "templates/**")
        })
    }

    configuration {
        moduleName = ""
        sourceRoot {
            path = "src"
        }
        skipEmptyPackages = true
        jdkVersion = 8
        includes = listOf("README.md")

        sourceLink {
            path = src
            url = "$githubRepo/blob/master/$src"
            lineSuffix = "#L"
        }
    }

    outputFormat = "html"
    outputDirectory = out

    description = "Generate ${project.name} v$version docs in HTML."

    doLast {
        println("Generated HTML format docs to $outputDirectory")
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
    val localProperties = Properties()
    val file = project.rootProject.file("local.properties")
    localProperties.load(file.inputStream())

    user = localProperties.getProperty("bintray.user")
    key = localProperties.getProperty("bintray.apikey")
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
        setLicenses("EPL-2.0", "GPL-2.0")
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

configureSpotless()
configureTesting()
