/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package net.bjonnh.rdf4k.examples

import net.nprod.rdf4k.Model.modelBuilder
import net.nprod.rdf4k.Model.namespace
import net.nprod.rdf4k.Model.subject
import net.nprod.rdf4k.Statement.o
import org.eclipse.rdf4j.model.Literal
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.vocabulary.DC

/**
 * RDF Tutorial example 04: Language tags
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example04LanguageTags {

    @JvmStatic
    fun main(args: Array<String>) {

        val vf = SimpleValueFactory.getInstance()

        // Create a new RDF model containing some information about the painting "The Potato Eaters"
        val model = modelBuilder {
            namespace("ex", "http://example.org/") {
                subject("ex:PotatoEaters") {
                    // In English, this painting is called "The Potato Eaters"
                    add(DC.TITLE, vf.createLiteral("The Potato Eaters", "en"))
                    // In Dutch, it's called "De Aardappeleters"
                    add(DC.TITLE, vf.createLiteral("De Aardappeleters", "nl"))
                }
            }
        }.build()

        // To see what's in our model, let's just print it to the screen
        for (st in model) {
            // we want to see the object values of each statement
            val value = st.o
            if (value is Literal) {
                println("language: " + value.language.orElse("unknown"))
                println(" title: " + value.label)
            }
        }
    }
}