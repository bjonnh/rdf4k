package net.nprod.rdf4k

import org.eclipse.rdf4j.model.Statement
import org.eclipse.rdf4j.model.Value
import org.eclipse.rdf4j.query.BindingSet
import org.eclipse.rdf4j.query.GraphQueryResult
import org.eclipse.rdf4j.query.QueryResult
import org.eclipse.rdf4j.query.TupleQueryResult
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