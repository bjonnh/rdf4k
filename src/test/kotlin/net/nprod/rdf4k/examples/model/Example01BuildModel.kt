/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.model

import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.impl.TreeModel
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF

/**
 * RDF Tutorial example 01: Building a simple RDF Model using Eclipse RDF4J
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example01BuildModel {

    @JvmStatic
    fun main(args: Array<String>) {

        // We use a ValueFactory to create the building blocks of our RDF statements: IRIs, blank nodes
        // and literals.
        val vf = SimpleValueFactory.getInstance()

        // We want to reuse this namespace when creating several building blocks.
        val ex = "http://example.org/"

        // Create IRIs for the resources we want to add.
        val picasso = vf.createIRI(ex, "Picasso")
        val artist = vf.createIRI(ex, "Artist")

        // Create a new, empty Model object.
        val model = TreeModel()

        // add our first statement: Picasso is an Artist
        model.add(picasso, RDF.TYPE, artist)

        // second statement: Picasso's first name is "Pablo".
        model.add(picasso, FOAF.FIRST_NAME, vf.createLiteral("Pablo"))

        // to see what's in our model, let's just print it to the screen
        model.map { statement ->
            println(statement)
        }
    }
}