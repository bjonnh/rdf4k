package net.nprod.rdf4k

import org.eclipse.rdf4j.model.Statement
import org.eclipse.rdf4j.model.Value
import org.eclipse.rdf4j.query.BindingSet
import org.eclipse.rdf4j.query.GraphQueryResult
import org.eclipse.rdf4j.query.TupleQueryResult
import org.eclipse.rdf4j.repository.RepositoryResult


/**
 * Iterates over the RepositoryResult<Statement> elements using *block*
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example13AddRDFToDatabase
 * @param block is a lambda handling each statement
 */
inline fun RepositoryResult<Statement>.map(block: (Statement) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }
}

/**
 * Iterates over the TupleQueryResult's BindingSet elements using *block*
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example15SimpleSPARQLQuery
 * @param block is a lambda handling each bindingSet
 */
inline fun TupleQueryResult.map(block: (BindingSet) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }
}

/**
 * Iterates over the GraphQueryResult's Statement elements using *block*
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.repository.Example16SPARQLConstructQuery
 * @param block is a lambda handling each statement
 */
inline fun GraphQueryResult.map(block: (Statement) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }
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