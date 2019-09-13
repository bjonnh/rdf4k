/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.examples.model.vocabulary.EX
import org.eclipse.rdf4j.model.util.Models
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

import java.io.IOException

/**
 * RDF Tutorial example 11: Getting a single property value for a particular subject.
 * In this example, we show how you can get retrieve each artist's first name and print it.
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example11SinglePropertyValue {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val filename = "example-data-artists.ttl"

        // read the file 'example-data-artists.ttl' as an InputStream.
        val input = javaClass.getResourceAsStream("/$filename")
        val model = Rio.parse(input, "", RDFFormat.TURTLE)

        // iterate over all resources that are of type 'ex:Artist'
        for (artist in model.filter(null, RDF.TYPE, EX.ARTIST).subjects()) {
            // get all RDF triples that denote values for the `foaf:firstName` property
            // of the current artist
            val firstNameTriples = model.filter(artist, FOAF.FIRST_NAME, null)

            // Get the actual first name by just selecting any property value. If no value can be found,
            // set the first name to '(unknown)'.
            val firstName = Models.objectString(firstNameTriples).orElse("(unknown)")

            println(artist.toString() + " has first name '$firstName'")
        }

    }
}