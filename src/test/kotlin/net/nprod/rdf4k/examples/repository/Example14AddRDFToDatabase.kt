/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.repository


import net.nprod.rdf4k.map
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.sail.memory.MemoryStore

import java.io.IOException

/**
 * RDF Tutorial example 14: Adding an RDF file directly to the database
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example14AddRDFToDatabase {

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

                // let's check that our data is actually in the database
                conn.getStatements(null, null, null).map { st ->
                    println("db contains: $st")
                }
            }
        } finally {
            // before our program exits, make sure the database is properly shut down.
            db.shutDown()
        }
    }
}