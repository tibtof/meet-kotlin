package train

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TrainTest : FreeSpec({
    val testTrain = Train(GenericTrain,
        listOf("10:00".hours to Station("Bucharest"),
            "20:00".hours to Station("Amsterdam")))

    "Train constructor should not accept" - {
        listOf(
            row("no stations", emptyList()),
            row("one station", listOf("10:00".hours to Station("Bucharest")))
        ).map { (description, schedule) ->
            description {
                shouldThrow<IllegalArgumentException> { Train(GenericTrain, schedule) }
            }
        }
    }

    "stations should be initialized correctly" {
        testTrain.stations shouldBe listOf(Station("Bucharest"), Station("Amsterdam"))
    }

    "timeAt should" - {
        "return the time for station en route" {
            testTrain.timeAt(Station("Bucharest")) shouldBe "10:00".hours
        }
        "return null for station not en route" {
            testTrain.timeAt(Station("Vienna")) shouldBe null
        }
    }

    "locationAt should return the train's location at the given time" - {
        listOf(
            row("09:00", Depot),
            row("10:00", Station("Bucharest")),
            row("11:00", EnRoute),
            row("20:00", Station("Amsterdam")),
            row("20:01", Depot)
        ).map { (time, expectedLocation) ->
            "$time - $expectedLocation" {
                retry(5 times pause(100.millis), until = { it is Station }) {
                    testTrain.locationAt(time.hours)
                } shouldBe expectedLocation
            }
        }
    }

})
