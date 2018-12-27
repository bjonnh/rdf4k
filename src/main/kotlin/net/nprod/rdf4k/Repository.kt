package net.nprod.rdf4k

import org.eclipse.rdf4j.model.Statement
import org.eclipse.rdf4j.model.Value
import org.eclipse.rdf4j.query.BindingSet
import org.eclipse.rdf4j.query.GraphQueryResult
import org.eclipse.rdf4j.query.TupleQueryResult
import org.eclipse.rdf4j.repository.RepositoryResult


inline fun RepositoryResult<Statement>.map(block: (Statement) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }

}

inline fun TupleQueryResult.map(block: (BindingSet) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }
}

inline fun GraphQueryResult.map(block: (Statement) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }
}


operator fun BindingSet.get(itemName: String): Value {
    return this.getValue(itemName)
}