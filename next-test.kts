#!/usr/bin/env kscript

import java.io.File

val nextTest = File(".tests").listFiles().sorted().first()
val origName = nextTest.name
val destName = origName.removeRange(0..origName.indexOf('_')) //range data structure
println("Moving ${nextTest.name}...") //string interpolation
nextTest.renameTo(File("src/test/kotlin/train/$destName"))
