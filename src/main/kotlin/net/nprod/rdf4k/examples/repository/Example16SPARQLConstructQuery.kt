/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.repository


import net.nprod.rdf4k.map
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.eclipse.rdf4j.sail.memory.MemoryStore

import java.io.IOException

/**
 * RDF Tutorial example 16: executing a SPARQL CONSTRUCT query on the database
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example16SPARQLConstructQuery {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new Repository.
        val db = SailRepository(MemoryStore())
        db.initialize()

        // Open a connection to the database
        try {
            db.connection.use { conn ->
                val filename = "example-data-artists.ttl"
                // add the RDF data from the inputstream directly to our database
                conn.add(javaClass.getResourceAsStream("/$filename"),
                    "",
                    RDFFormat.TURTLE)

                // We do a simple SPARQL CONSTRUCT-query that retrieves all statements about artists,
                // and their first names.
                val queryString = """
                    |PREFIX ex: <http://example.org/>
                    |PREFIX foaf: <${FOAF.NAMESPACE}>
                    |CONSTRUCT
                    |WHERE {
                    |    ?s a ex:Artist;
                    |       foaf:firstName ?n .
                    |}""".trimMargin()

                val query = conn.prepareGraphQuery(queryString)

                val turtleWriter = Rio.createWriter(RDFFormat.TURTLE, System.out)
                query.evaluate(turtleWriter)

                // A QueryResult is also an AutoCloseable resource, so make sure it gets closed when done.
                // This was needed explicitly in Java, but not necessary in Kotlin
                query.evaluate().map { st ->
                        // ... and print them out
                        println(st)
                }
            }
        } finally {
            // Before our program exits, make sure the database is properly shut down.
            db.shutDown()
        }
    }
}