/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.*
import org.eclipse.rdf4j.model.*
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

import java.io.IOException

/**
 * RDF Tutorial example 09: Reading a Turtle syntax file to create a Model
 *
 * In this example, we show how you can use the Rio Parser/writer toolkit to read files
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 * @see
 */
object Example09Filter {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val filename = "example-data-artists.ttl"

        // read the file 'example-data-artists.ttl' as an InputStream.
        val input = javaClass.getResourceAsStream("/$filename")


        // Rio also accepts a java.io.Reader as input for the parser.
        val model = Rio.parse(input, "", RDFFormat.TURTLE)

        val vf = SimpleValueFactory.getInstance()

        // We want to find all information about the artist `ex:VanGogh`.
        val vanGogh = vf.createIRI("http://example.org/VanGogh")

        // By filtering on a specific subject we zoom in on the data that is about that subject.
        // The filter method takes a subject, predicate, object (and optionally a named graph/context)
        // argument. The more arguments we set to a value, the more specific the filter becomes.
        val aboutVanGogh = model.filter(vanGogh, null, null)

        // Iterate over the statements that are about Van Gogh
        aboutVanGogh.map { st ->
            // the subject will always be `ex:VanGogh`, an IRI, so we can safely cast it
            val subject = st.s as IRI
            // the property predicate can be anything, but it's always an IRI
            val predicate = st.p

            // the property value could be an IRI, a BNode, or a Literal. In RDF4J, Value is
            // is the supertype of all possible kinds of RDF values.
            val obj = st.o

            // let's print out the statement in a nice way. We ignore the namespaces and only print the
            // local name of each IRI
            print(subject.localName + " " + predicate.localName + " ")
            when (obj) {
                // it's a literal value. Let's print it out nicely, in quotes, and without any ugly
                // datatype stuff
                is Literal -> println("\"${obj.label}\"")
                // it's an IRI. Just print out the local part (without the namespace)
                is IRI -> println(obj.localName)
                // it's a blank node. Just print it out as-is.
                else -> println(obj)
            }
        }
    }
}