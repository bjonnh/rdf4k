package net.nprod.rdf4k

import org.eclipse.rdf4j.model.Statement
import org.eclipse.rdf4j.repository.RepositoryResult


fun RepositoryResult<Statement>.map(block: (Statement) -> Unit) {
    while (this.hasNext()) {
        val st = this.next()
        block(st)
    }

}
