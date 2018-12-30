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


# Help received

Thanks to *Campbell Jones* for the Gradle/Bintray help.