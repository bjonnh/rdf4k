/*******************************************************************************
 * Copyright (c) 2017-2018 Eclipse RDF4J contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *******************************************************************************/

package net.nprod.rdf4k.examples.function


import org.eclipse.rdf4j.model.Literal
import org.eclipse.rdf4j.model.Value
import org.eclipse.rdf4j.model.ValueFactory
import org.eclipse.rdf4j.query.algebra.evaluation.ValueExprEvaluationException
import org.eclipse.rdf4j.query.algebra.evaluation.function.Function

/**
 * An example custom SPARQL function that detects palindromes
 *
 * @author Jeen Broekstra
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.function.ExampleK01FunctionInSPARQL
 */
class PalindromeFunction : Function {

    /**
     * return the URI 'http://example.org/custom-function/palindrome' as a
     * String
     */
    override fun getURI(): String {
        return NAMESPACE + "palindrome"
    }

    /**
     * Executes the palindrome function.
     *
     * @return A boolean literal representing true if the input argument is a
     * palindrome, false otherwise.
     * @throws ValueExprEvaluationException
     * if more than one argument is supplied or if the supplied argument
     * is not a literal.
     */
    @Throws(ValueExprEvaluationException::class)
    override fun evaluate(valueFactory: ValueFactory, vararg args: Value): Value {
        // our palindrome function expects only a single argument, so throw an error
        // if there's more than one
        if (args.size != 1) {
            throw ValueExprEvaluationException(
                "palindrome function requires" + "exactly 1 argument, got "
                        + args.size
            )
        }
        val arg = args[0]
        // check if the argument is a literal, if not, we throw an error
        if (arg !is Literal) {
            throw ValueExprEvaluationException(
                "invalid argument (literal expected): $arg"
            )
        }

        // get the actual string value that we want to check for palindrome-ness.
        val label = arg.label
        // we invert our string


        val inverted = label.reversed()

        // a string is a palindrome if it is equal to its own inverse
        val palindrome = inverted.equals(label, ignoreCase = true)

        // a function is always expected to return a Value object, so we
        // return our boolean result as a Literal
        return valueFactory.createLiteral(palindrome)
    }

    companion object {

        // define a constant for the namespace of our custom function
        const val NAMESPACE = "http://example.org/custom-function/"
    }
}