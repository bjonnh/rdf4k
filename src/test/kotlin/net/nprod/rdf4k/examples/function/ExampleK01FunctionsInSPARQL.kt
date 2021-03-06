/*******************************************************************************
 * Copyright (c) 2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.function

import net.nprod.rdf4k.get
import net.nprod.rdf4k.map
import net.nprod.rdf4k.modelBuilder
import net.nprod.rdf4k.namespace
import net.nprod.rdf4k.subject
import org.eclipse.rdf4j.model.vocabulary.RDFS
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.sail.memory.MemoryStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * RDF Tutorial example K01: Implementing a function for SPARQL queries
 *
 * Refer to that documentation: [http://docs.rdf4j.org/custom-sparql-functions/]
 *
 * The function is described in resources/META-INF/services/org.eclipse.rdf4j.query.algebra.evaluation.function.Function
 *
 * Running this object main file in IntelliJ should work out of the box. If you produce the project with gradle
 *
 * make sure the resources are copied in the JAR.
 *
 * @author Jonathan Bisson
 */
class ExampleK01FunctionInSPARQL {

    @Test
    fun `functions in SPARQL`() {


        // We are using the adapted Kotlin DSL here
        val model = modelBuilder {
            namespace("ex", "http://example.org/")
            subject("ex:a") {
                add(RDFS.LABEL, "step on no pets")
            }
            subject("ex:b") {
                add(RDFS.LABEL, "go on, try it")
            }
        }.build()

        val queryString = """
            | PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            | PREFIX cfn: <http://example.org/custom-function/>
            | SELECT ?x ?label
            | WHERE {
            |   ?x rdfs:label ?label .
            |   FILTER(cfn:palindrome(str(?label)))
            | }""".trimMargin()

        val db = SailRepository(MemoryStore())
        db.init()
        // Open a connection to the database
        val output = try {
            db.connection.run {
                // add the model
                add(model)
                val query = prepareTupleQuery(queryString)
                query.evaluate().map {
                    it["x"].toString() to it["label"].toString()
                }.toMap()
            }
        } finally {
            // before our program exits, make sure the database is properly shut down.
            // However as we are just running tests and this step is taking a lot of time, we do not do it.
            // in a real program, you really want to do that.
            //db.shutDown()
        }

        assertEquals(mapOf("http://example.org/a" to "\"step on no pets\""), output)
    }
}

