/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
package net.nprod.rdf4k.examples.model

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

import java.io.IOException

/**
 * RDF Tutorial example 08: Reading a Turtle syntax file to create a Model
 *
 * In this example, we show how you can use the Rio Parser/writer toolkit to read files
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 * @see
 */
object Example08ReadTurtle {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val filename = "example-data-artists.ttl"

        // read the file 'example-data-artists.ttl' as an InputStream.
        val input = javaClass.getResourceAsStream("/$filename")

        // Rio also accepts a java.io.Reader as input for the parser.
        val model = Rio.parse(input, "", RDFFormat.TURTLE)

        // To check that we have correctly read the file, let's print out the model to the screen again
        model.map { statement ->
            println(statement)
        }
    }
}