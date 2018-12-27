/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.repository

import net.nprod.rdf4k.map
import java.io.IOException

import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.eclipse.rdf4j.sail.memory.MemoryStore

/**
 * RDF Tutorial example 13: Adding an RDF Model to a database
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */

object Example13AddRDFToDatabase {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        // First load our RDF file as a Model.
        val filename = "example-data-artists.ttl"
        val input = javaClass.getResourceAsStream("/$filename")
        val model = Rio.parse(input, "", RDFFormat.TURTLE)

        // Create a new Repository. Here, we choose a database implementation
        // that simply stores everything in main memory. Obviously, for most real-life applications, you will
        // want a different database implementation, that can handle large amounts of data without running
        // out of memory and keeps data safely on disk.
        // See http://docs.rdf4j.org/programming/#_the_repository_api for more extensive examples on
        // how to create and maintain different types of databases.
        val db = SailRepository(MemoryStore())
        db.initialize()

        // Open a connection to the database
        try {
            db.connection.apply {
                // add the model
                add(model)

                // let's check that our data is actually in the database
                getStatements(null, null, null).map {
                        println("db contains: $it")
                }
            }
        } finally {
            // before our program exits, make sure the database is properly shut down.
            db.shutDown()
        }
    }
}