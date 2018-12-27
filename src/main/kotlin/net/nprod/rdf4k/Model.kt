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

fun modelBuilder(block: ModelBuilder.() -> Unit): ModelBuilder = ModelBuilder().apply(block)

fun ModelBuilder.namespace(prefix: String, namespace: String): ModelBuilder {
    return this.setNamespace(prefix, namespace)
}

fun ModelBuilder.subject(prefixedNameOriri: String, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.subject(prefixedNameOriri).block()
}

fun ModelBuilder.namedGraph(graphName: String, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.namedGraph(graphName).block()
}


fun ModelBuilder.subject(node: BNode, block: ModelBuilder.() -> ModelBuilder): ModelBuilder {
    return this.subject(node).block()
}