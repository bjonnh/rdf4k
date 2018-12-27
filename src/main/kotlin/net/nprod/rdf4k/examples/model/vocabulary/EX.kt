/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/
package net.nprod.rdf4k.examples.model.vocabulary


import org.eclipse.rdf4j.model.IRI
import org.eclipse.rdf4j.model.impl.SimpleValueFactory

/**
 * Vocabulary constants for the 'http://example.org/' namespace.
 * It's a good idea to always create a vocabulary class such as this one when you program with RDF4J. It makes
 * it far easier to reuse certain resources and properties in various places in your code.
 */
object EX {

    /**
     * The full namespace: "http://example.org/".
     */
    val NAMESPACE = "http://example.org/"

    /**
     * The prefix usually used for this vocabulary: 'ex'.
     */
    val PREFIX = "ex"

    /**
     * The `ex:creatorOf` property.
     */
    val CREATOR_OF = getIRI("creatorOf")

    /**
     * The `ex:Artist` class.
     */
    val ARTIST = getIRI("Artist")

    /**
     * Creates a new [IRI] with this vocabulary's namespace for the given local name.
     *
     * @param localName a local name of an IRI, e.g. 'creatorOf', 'name', 'Artist', etc.
     * @return an IRI using the http://example.org/ namespace and the given local name.
     */
    private fun getIRI(localName: String): IRI {
        return SimpleValueFactory.getInstance().createIRI(NAMESPACE, localName)
    }

}