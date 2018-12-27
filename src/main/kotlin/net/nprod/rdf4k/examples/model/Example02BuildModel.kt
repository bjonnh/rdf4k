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
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF

/**
 * RDF Tutorial example 02: Building a simple RDF Model using the RDF4J ModelBuilder
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example02BuildModel {

    @JvmStatic
    fun main(args: Array<String>) {
        // We are using the adapted Kotlin DSL here
        val model = modelBuilder {
            namespace("ex", "http://example.org/")
            subject("ex:Picasso") {
                add(RDF.TYPE, "ex:Artist")
                add(FOAF.FIRST_NAME, "Pablo")
            }
        }.build()

        // To see what's in our model, let's just print it to the screen
        model.map { statement ->
            println(statement)
        }
    }
}

