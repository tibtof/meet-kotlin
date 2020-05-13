#!/usr/bin/env kscript

import java.io.File

val nextSolution = File(".solutions").listFiles().sorted().first()
val origName = nextSolution.name
val dest = origName.removeRange(0..origName.indexOf('_'))
println("Moving ${nextSolution.name} to $dest")
nextSolution.renameTo(File("$dest"))
