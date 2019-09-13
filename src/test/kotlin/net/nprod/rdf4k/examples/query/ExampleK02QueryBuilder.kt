/*******************************************************************************
 * Copyright (c) 2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/


package net.nprod.rdf4k.examples.query

import net.nprod.rdf4k.*
import org.eclipse.rdf4j.model.vocabulary.FOAF
import org.eclipse.rdf4j.model.vocabulary.RDF
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.sail.memory.MemoryStore
import org.eclipse.rdf4j.sparqlbuilder.core.Variable
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery
import org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri


/**
 * RDF Tutorial example K02: Using the Query Builder as a DSL
 *
 * Refer to that documentation: [http://docs.rdf4j.org/sparqlbuilder/]
 *
 *
 * @author Jonathan Bisson
 */
object ExampleK02QueryBuilder {

    @JvmStatic
    fun main(args: Array<String>) {


        // We are using the adapted Kotlin DSL here
        val model = modelBuilder {
            namespace("ex", "http://example.org/")
            subject("ex:a") {
                add(FOAF.NAME, "Katherine Johnson")
                add(RDF.TYPE, FOAF.PERSON)
            }
            subject("ex:b") {
                add(FOAF.NAME, "Grace Hopper")
                add(RDF.TYPE, FOAF.PERSON)
            }
        }.build()

        val foafPrefix = sparqlPrefix("foaf", iri(FOAF.NAMESPACE))

        val name = sparqlVariable("name")
        val x = sparqlVariable("x")
        val selectQuery = (selectQuery {
            prefix(foafPrefix)
            // Could also have used directly
            // prefix("foaf", iri("http://xmlns.com/foaf/0.1/"))
            select(name) {
                where(
                        x.has(foafPrefix.iri("name"), name),
                        x.isA(foafPrefix.iri("Person"))
                )
            }
        } orderBy name limit 5 offset 0 ).queryString
        println(selectQuery)
        val db = SailRepository(MemoryStore())
        db.initialize()
        // Open a connection to the database
        try {
            db.connection.apply {
                // add the model
                add(model)
                val query = prepareTupleQuery(selectQuery)
                query.evaluate().map {
                    // ... and print out the value of the variable binding for ?name
                    println("name = ${it["name"]}")
                }
            }
        } finally {
            // before our program exits, make sure the database is properly shut down.
            db.shutDown()
        }
    }
}

infix fun SelectQuery.orderBy(variable: Variable) = this.orderBy(variable)
infix fun SelectQuery.limit(num: Int) = this.limit(num)
infix fun SelectQuery.offset(num: Int) = this.offset(num)