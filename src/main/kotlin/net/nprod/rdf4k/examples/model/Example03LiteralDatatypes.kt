/*******************************************************************************
 * Copyright (c) 2016-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.model

import net.nprod.rdf4k.*
import net.nprod.rdf4k.modelBuilder
import net.nprod.rdf4k.namespace
import net.nprod.rdf4k.subject

import org.eclipse.rdf4j.model.Literal
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.vocabulary.XMLSchema


/**
 * RDF Tutorial example 03: Datatyped literals
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 */
object Example03LiteralDatatypes {

    @JvmStatic
    fun main(args: Array<String>) {

        val vf = SimpleValueFactory.getInstance()

        // Create a new RDF model containing information about the painting "The Potato Eaters"

        val model = modelBuilder {
            namespace("ex", "http://example.org/") {
                subject("ex:PotatoEaters") {
                    // this painting was created on April 1, 1885
                    add("ex:creationDate", vf.createLiteral("1885-04-01T00:00:00Z", XMLSchema.DATETIME))
                    // You can also pass in a Java Date object directly:
                    //.add("ex:creationDate", new GregorianCalendar(1885, Calendar.APRIL, 1).getTime())

                    // the painting shows 5 people
                    add("ex:peopleDepicted", 5)
                }
            }
        }.build()

        // To see what's in our model, let's just print stuff to the screen
        model.map { st ->
            // we want to see the object values of each property
            val property = st.p
            val value = st.o

            if (value is Literal) {
                println("datatype: " + value.datatype)

                // get the value of the literal directly as a Java primitive.
                when (property.localName) {
                    "peopleDepicted" -> {
                        val peopleDepicted = value.intValue()
                        println(peopleDepicted.toString() + " people are depicted in this painting")
                    }
                    "creationDate" -> {
                        val calendar = value.calendarValue()
                        val date = calendar.toGregorianCalendar().time
                        println("The painting was created on $date")
                    }
                }

                // you can also just get the lexical value (a string) without worrying about the datatype
                println("Lexical value: '" + value.label + "'")
            }
        }
    }
}