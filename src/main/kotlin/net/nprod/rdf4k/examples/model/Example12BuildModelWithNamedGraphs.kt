/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.examples.model.vocabulary.EX
import net.nprod.rdf4k.modelBuilder
import net.nprod.rdf4k.namedGraph
import net.nprod.rdf4k.namespace
import net.nprod.rdf4k.subject
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

import java.io.IOException

/**
 * RDF Tutorial example 12: Building a Model with named graphs
 *
 * In this example, we show how you can use the context mechanism to add statements separate named
 * graphs within the same Model.
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example12BuildModelWithNamedGraphs {

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // We'll use a ModelBuilder to create two named graphs, one containing data about
        // picasso, the other about Van Gogh.
        val builder = modelBuilder {
            namespace("ex", "http://example.org/")

            // in named graph 1, we add info about Picasso
            namedGraph("ex:namedGraph1") {
                subject("ex:Picasso") {
                    add(RDF.TYPE, EX.ARTIST)
                    add(FOAF.FIRST_NAME, "Pablo")
                }
            }

            // in named graph2, we add info about Van Gogh.
            namedGraph("ex:namedGraph2") {
                subject("ex:VanGogh") {
                    add(RDF.TYPE, EX.ARTIST)
                    add(FOAF.FIRST_NAME, "Vincent")
                }
            }
        }

        // We're done building, create our Model
        val model = builder.build()

        // each named graph is stored as a separate context in our Model
        model.contexts().map { context ->
            println("Named graph $context contains: ")

            // write _only_ the statemements in the current named graph to the console, in N-Triples format
            Rio.write(model.filter(null, null, null, context), System.out, RDFFormat.NTRIPLES)
            println()
        }
    }
}