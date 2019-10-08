/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.repository


import net.nprod.rdf4k.get
import net.nprod.rdf4k.map
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.sail.memory.MemoryStore

import java.io.IOException

/**
 * RDF Tutorial example 15: executing a simple SPARQL query on the database
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example15SimpleSPARQLQuery {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new Repository.
        val db = SailRepository(MemoryStore())
        db.init()

        // Open a connection to the database
        try {
            db.connection.use { conn ->
                val filename = "example-data-artists.ttl"

                // add the RDF data from the inputstream directly to our database
                conn.add(javaClass.getResourceAsStream("/$filename"), "", RDFFormat.TURTLE)

                // We do a simple SPARQL SELECT-query that retrieves all resources of type `ex:Artist`,
                // and their first names.
                val queryString = """
                    |PREFIX ex: <http://example.org/>
                    |PREFIX foaf: <${FOAF.NAMESPACE}>
                    |SELECT ?s ?n
                    |WHERE {
                    |    ?s a ex:Artist;
                    |       foaf:firstName ?n .
                    |}""".trimMargin()

                val query = conn.prepareTupleQuery(queryString)

                // A QueryResult is also an AutoCloseable resource, so make sure it gets closed when done.
                // we just iterate over all solutions in the result...
                query.evaluate().map {
                    // ... and print out the value of the variable binding for ?s and ?n
                    println("?s = " + it["s"])
                    println("?n = " + it["n"])
                }
            }
        } finally {
            // Before our program exits, make sure the database is properly shut down.
            db.shutDown()
        }
    }
}