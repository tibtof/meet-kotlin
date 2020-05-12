#!/usr/bin/env kscript

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

val nextTest = File(".tests").listFiles()[0]
val destinationPath = "src/test/kotlin/train"
val destinationTest = nextTest.name.removeRange(0..nextTest.name.indexOf('_'))
println("Moving ${nextTest.name} to $destinationPath/$destinationTest")
nextTest.renameTo(File("$destinationPath/$destinationTest"))
