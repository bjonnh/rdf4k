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

import org.eclipse.rdf4j.sparqlbuilder.core.Prefix
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder
import org.eclipse.rdf4j.sparqlbuilder.core.Variable
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri


/**
 * Allows to construct a SPARQL Query using Kotlin's DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.query.ExampleK02QueryBuilder
 */
fun selectQuery(block: SelectQuery.() -> Unit): SelectQuery = Queries.SELECT().apply(block)


/**
 * Allows to insert a select in a SPARQL Query using Kotlin's DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.query.ExampleK02QueryBuilder
 */
fun SelectQuery.select(name: Variable?, function: SelectQuery.() -> SelectQuery): SelectQuery {
    return this.select(name).function()
}


/**
 * Allows to insert a prefix in a SPARQL Query using Kotlin's DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.query.ExampleK02QueryBuilder
 */
fun SelectQuery.prefix(name: String, iri: Iri): SelectQuery {
    return this.prefix(sparqlPrefix(name, iri))
}


/**
 * A helper function to help write SPARQL variables in the DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.query.ExampleK02QueryBuilder
 */
fun sparqlVariable(name: String): Variable = SparqlBuilder.`var`(name)


/**
 * A helper function to help write SPARQL prefix in the DSL
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.query.ExampleK02QueryBuilder
 */
fun sparqlPrefix(name: String, iri: Iri): Prefix = SparqlBuilder.prefix(name, iri)
