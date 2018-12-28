# Package net.nprod.rdf4k 
A wrapper around [RDF4J](https://www.rdf4j.org) for [Kotlin](https://www.kotlinlang.org). It allows writing
code using Kotlin's DSL.

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
