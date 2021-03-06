[ ![Download](https://api.bintray.com/packages/bjonnh/RDF4K/RDF4K/images/download.svg) ](https://bintray.com/bjonnh/RDF4K/RDF4K/_latestVersion)
# Package net.nprod.rdf4k 
A wrapper around [RDF4J](https://www.rdf4j.org) for [Kotlin](https://www.kotlinlang.org). It allows writing
code using Kotlin's DSL. Documentation can be found [here](https://bjonnh.github.io/rdf4k/). Code can be found [here](https://github.com/bjonnh/rdf4k).

You can now write things such as 

```kotlin
val model = modelBuilder {
                namespace("ex", "http://example.org/")
                subject("ex:Picasso") {
                    add(RDF.TYPE, "ex:Artist")
                    add(FOAF.FIRST_NAME, "Pablo")
                }
            }.build()


model.map { statement ->
    println(statement)
}
```

# How to use

Here is a minimal build.gradle.kts
```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "net.mygreatprogram"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://dl.bintray.com/bjonnh/RDF4K")}
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("net.nprod:rdf4k:0.1.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
```

There are a lot of examples in src/test that showcase the different features of both RDF4K and RDF4J.
 
# How to publish (developers)

The project is published on bintray, use the gradle tasks bintrayPublish then bintrayUpload.
It will require that you have bintray access to that repository obviously. 

The project is using [Batect](https://github.com/charleskorn/batect/) for testing and building in a standardised way. 

To verify the project builds, run:
```shell script
$ ./batect check
```  

Before a new release please run as well:
```shell script
$ ./batect outdated
```
# Help received

Thanks to *Campbell Jones* for the Gradle/Bintray help.