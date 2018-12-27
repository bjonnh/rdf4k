/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.examples.model.vocabulary.EX
import org.eclipse.rdf4j.model.Resource
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import java.io.IOException


/**
 * RDF Tutorial example 10: Getting all values of a property for a particular subject.
 *
 * In this example, we show how you can get retrieve all paintings that Van Gogh created.
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example10PropertyValues {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val filename = "example-data-artists.ttl"

        // read the file 'example-data-artists.ttl' as an InputStream.
        val input = javaClass.getResourceAsStream("/$filename")
        val model = Rio.parse(input, "", RDFFormat.TURTLE)

        val vf = SimpleValueFactory.getInstance()

        // We want to find all information about the artist `ex:VanGogh`.
        val vanGogh = vf.createIRI(EX.NAMESPACE, "VanGogh")

        // Retrieve all values of the `ex:creatorOf` property for Van Gogh. These will be
        // resources that identify paintings by Van Gogh.
        val paintings = model.filter(vanGogh, EX.CREATOR_OF, null).objects()
        paintings.map { painting ->
            if (painting is Resource) {
                // our value is either an IRI or a blank node. Retrieve its properties and print.
                val paintingProperties = model.filter(painting as Resource, null, null)

                // write the info about this painting to the console in Turtle format
                println("--- information about painting: $painting")
                Rio.write(paintingProperties, System.out, RDFFormat.TURTLE)
                println()
            }
        }

    }
}