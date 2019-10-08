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

plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    jcenter()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    compile(group = "com.diffplug.spotless", name = "spotless-plugin-gradle", version = "3.24.2")
    compile(group = "io.codearte.gradle.nexus", name = "gradle-nexus-staging-plugin", version = "0.12.0")
    compile(group = "me.champeau.gradle", name = "jmh-gradle-plugin", version = "0.4.7")
    compile(group = "org.ajoberstar.reckon", name = "reckon-gradle", version = "0.9.0")
}
