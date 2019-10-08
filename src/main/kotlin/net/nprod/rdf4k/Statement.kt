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

import org.eclipse.rdf4j.model.Statement

/**
 * Gather the *subject* of the statement
 *
 * @author Jonathan Bisson
 */
val Statement.s get() = this.subject

/**
 * Gather the *property* of the statement
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example03LiteralDatatypes
 */
val Statement.p get() = this.predicate

/**
 * Gather the *object* of the statement
 *
 * @author Jonathan Bisson
 * @sample net.nprod.rdf4k.examples.model.Example03LiteralDatatypes
 */
val Statement.o get() = this.getObject()
