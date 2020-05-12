#!/usr/bin/env kscript

import java.io.File

val nextSolution = File(".solutions").listFiles().sorted().first()
val dest = nextTest.name.removeRange(0..nextTest.name.indexOf('_'))
println("Moving ${nextTest.name} to $dest")
nextTest.renameTo(File("$dest"))
