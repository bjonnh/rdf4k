/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.modelBuilder
import net.nprod.rdf4k.namespace
import net.nprod.rdf4k.subject
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

/**
 * RDF Tutorial example 06: Writing an RDF model in RDF/XML syntax
 *
 * In this example, we show how you can use the Rio Parser/writer toolkit to write your
 * model in RDF/XML syntax.
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example06WriteRdfXml {

    @JvmStatic
    fun main(args: Array<String>) {

        // To create a blank node for the address, we need a ValueFactory
        val vf = SimpleValueFactory.getInstance()
        val address = vf.createBNode()

        // Identically to example 03, we create a model with some data
        val model = modelBuilder {
            namespace("ex", "http://example.org/")

            subject("ex:Picasso") {
                add(RDF.TYPE, "ex:Artist")
                add(FOAF.FIRST_NAME, "Pablo")
                add("ex:homeAddress", address) // link the blank node
            }
            subject(address) {
                // switch the subject
                add("ex:street", "31 Art Gallery")
                add("ex:city", "Madrid")
                add("ex:country", "Spain")
            }
        }.build()

        // Instead of simply printing the statements to the screen, we use a Rio writer to
        // write the model in RDF/XML syntax:
        Rio.write(model, System.out, RDFFormat.RDFXML)

    // Note that instead of writing to the screen using `System.out` you could also provide
    // a java.io.FileOutputStream or a java.io.FileWriter to save the model to a file
    }
}