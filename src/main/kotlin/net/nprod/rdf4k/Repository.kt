/*
 * Copyright (c) 2019 Jonathan Bisson
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
 */

package net.nprod.rdf4k

import org.eclipse.rdf4j.model.Value
import org.eclipse.rdf4j.query.BindingSet
import org.eclipse.rdf4j.query.QueryResult
import org.eclipse.rdf4j.repository.RepositoryResult

/**
 * Iterates over the RepositoryResult<Statement> elements using *block*
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example13AddRDFToDatabase
 * @param block is a lambda handling each statement
 */
inline fun <T, R> RepositoryResult<T>.map(transform: (T) -> R): List<R> {
    val list: ArrayList<R> = arrayListOf()

    while (this.hasNext()) {
        val st = this.next()
        list.add(transform(st))
    }

    return list
}

/**
 * Iterates over the TupleQueryResult's BindingSet elements using *block*
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example15SimpleSPARQLQuery
 * @sample net.nprod.rdf4k.examples.repository.Example16SPARQLConstructQuery
 * @param block is a lambda handling each bindingSet
 */
inline fun <T, R> QueryResult<T>.map(transform: (T) -> R): List<R> {
    val list: ArrayList<R> = arrayListOf()

    while (this.hasNext()) {
        val st = this.next()
        list.add(transform(st))
    }

    return list
}

/**
 * Gets a named binding from the BindingSet.
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example15SimpleSPARQLQuery
 * @param itemName is the name of the binding
 */
operator fun BindingSet.get(itemName: String): Value {
    return this.getValue(itemName)
}
