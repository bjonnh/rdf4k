/********************************************************************************
 * Copyright (c) 2018 Jonathan Bisson
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/

package net.nprod.rdf4k

import org.eclipse.rdf4j.model.BNode
import org.eclipse.rdf4j.model.util.ModelBuilder

/**
 * Allows to construct a ModelBuilder using Kotlin's DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example02BuildModel
 */
fun modelBuilder(block: ModelBuilder.() -> Unit): ModelBuilder = ModelBuilder().apply(block)

/**
 * Adds a namespace to a given ModelBuilder
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example02BuildModel
 */
fun ModelBuilder.namespace(prefix: String, namespace: String): ModelBuilder {
    return this.setNamespace(prefix, namespace)
}

/**
 * Adds a subject to a given ModelBuilder
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example02BuildModel
 */
fun ModelBuilder.subject(prefixedNameOrIRI: String, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.subject(prefixedNameOrIRI).block()
}

/**
 * Adds a subject to a given ModelBuilder, returns any type
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example02BuildModel
 */
fun ModelBuilder.subject(prefixedNameOrIRI: String, block: ModelBuilder.() -> Any?): Any? {
    return this.subject(prefixedNameOrIRI).block()
}


/**
 * Adds a subject from a node to a given ModelBuilder
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example02BuildModel
 */
fun ModelBuilder.subject(node: BNode, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.subject(node).block()
}

/**
 * Adds a named graph to a given ModelBuilder
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example12BuildModelWithNamedGraphs
 */
fun ModelBuilder.namedGraph(graphName: String, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.namedGraph(graphName).block()
}

